package SDK.Lib.Resource.Download;

import SDK.Lib.FrameWork.Ctx;
import SDK.Lib.FrameWork.MacroDef;
import SDK.Lib.Log.LogTypeId;
import SDK.Lib.Resource.ResLoadData.ResPackType;
import SDK.Lib.Tools.UtilApi;
import SDK.Lib.Tools.UtilStr;

/**
* @brief 使用 Unity 的 UnityWebRequest 从网络下载数据
* @ref http://blog.csdn.net/u010019717/article/details/52753738
* @brief 这个加载如果目录中有空格，会导致加载失败的，例如 assets/resources/flares/art assets/digicam.unity3d 会加载失败
*/
public class WebRequestDownloadItem extends DownloadItem
{
    protected UnityWebRequest mUnityWebRequestFile;
    protected String mErrorStr; // 如果加载失败，显示错误字符串

    public WebRequestDownloadItem()
    {
        this.mUnityWebRequestFile = null;
        this.mErrorStr = "";
    }

    @Override
    public void reset()
    {
        super.reset();

        if (null != this.mUnityWebRequestFile)
        {
            this.mUnityWebRequestFile.Dispose();
            this.mUnityWebRequestFile = null;
        }
    }

    @Override
    public UnityWebRequest getUnityWebRequestFile()
    {
        return null;
    }

    @Override
    public void load()
    {
        super.load();

        Ctx.mInstance.mCoroutineMgr.StartCoroutine(this.downloadAsset());
    }

    // mPath 是这个格式 http://127.0.0.1/UnityServer/Version.txt?ver=100
    @Override
    protected IEnumerator downloadAsset()
    {
        //this.deleteFromCache(this.mDownloadVerPath);

        if (this.mResPackType == ResPackType.eBundleType)
        {
            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log(string.Format("WebRequestDownloadItem::downloadAsset, download AssetBundles, DownloadNoVerPath = {0}", this.mDownloadNoVerPath), LogTypeId.eLogDownload);
            }

            this.mUnityWebRequestFile = UnityWebRequest.Get(this.mDownloadVerPath);
        }
        else
        {
            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log(string.Format("WebRequestDownloadItem::downloadAsset, download CommonFile, DownloadVerPath = {0}", this.mDownloadVerPath), LogTypeId.eLogDownload);
            }

            this.mUnityWebRequestFile = UnityWebRequest.Get(this.mDownloadVerPath);
        }

        // https://docs.unity3d.com/ScriptReference/Networking.UnityWebRequest.html
        // https://docs.unity3d.com/ScriptReference/Networking.UnityWebRequest-timeout.html
        //this.mUnityWebRequestFile.timeout = 5;  // 尝试 5 秒后丢弃，但是这个如果在移动平台如果超时会抛出一个异常

        if (MacroDef.ENABLE_LOG)
        {
            if (null != this.mUnityWebRequestFile)
            {
                Ctx.mInstance.mLogSys.log(string.Format("WebRequestDownloadItem::downloadAsset, front, www not null, path = {0}", this.mLoadPath), LogTypeId.eLogDownload);
                this.logInfo();
            }
            else
            {
                Ctx.mInstance.mLogSys.log(string.Format("WebRequestDownloadItem::downloadAsset, front, www is null, path = {0}", this.mLoadPath), LogTypeId.eLogDownload);
                this.logInfo();
            }
        }

        yield return this.mUnityWebRequestFile.Send();

        if (MacroDef.ENABLE_LOG)
        {
            if (null != this.mUnityWebRequestFile)
            {
                Ctx.mInstance.mLogSys.log(string.Format("WebRequestDownloadItem::downloadAsset, back, www not null, path = {0}", this.mLoadPath), LogTypeId.eLogDownload);
                this.logInfo();
            }
            else
            {
                Ctx.mInstance.mLogSys.log(string.Format("WebRequestDownloadItem::downloadAsset, back, www is null, path = {0}", this.mLoadPath), LogTypeId.eLogDownload);
                this.logInfo();
            }
        }

        this.onWWWEnd();
    }

    // 加载完成回调处理
    @Override
    protected void onWWWEnd()
    {
        // 只有 UnityWebRequest::responseCode 是 200 的时候，才显示加载成功，UnityWebRequest::error == null 并且 UnityWebRequest::isDone == true ，并不能说明加载成功
        if (null != this.mUnityWebRequestFile && UtilApi.isUnityWebRequestLoadedSuccess(this.mUnityWebRequestFile))
        {
            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log(String.format("DownloadItem::onWWWEnd, success, path = %s", this.mLoadPath), LogTypeId.eLogDownload);
            }

            // 如果是 AssetBudnles 资源，并且使用 WWW.LoadFromCacheOrDownload， WWW.size 和 WWW.bytes 这些属性是不能访问的，只能访问 WWW.assetBundle 这个属性
            if (this.mResPackType == ResPackType.eBundleType)
            {
                // this.mW3File.assetBundle
                if (this.mUnityWebRequestFile.downloadHandler.isDone &&
                    (this.mUnityWebRequestFile.downloadHandler.data.Length > 0 ||
                    this.mUnityWebRequestFile.downloadHandler.text.Length > 0))
                {
                    if (this.mUnityWebRequestFile.downloadHandler.data != null)
                    {
                        this.mBytes = this.mUnityWebRequestFile.downloadHandler.data;
                    }
                    else if (this.mUnityWebRequestFile.downloadHandler.text != null)
                    {
                        this.mText = this.mUnityWebRequestFile.downloadHandler.text;
                    }
                }

                if (this.mIsWriteFile)
                {
                    this.writeFile();
                }
            }
            else
            {
                if (this.mUnityWebRequestFile.downloadHandler.isDone &&
                    (this.mUnityWebRequestFile.downloadHandler.data.Length > 0 ||
                    this.mUnityWebRequestFile.downloadHandler.text.Length > 0))
                {
                    if (this.mUnityWebRequestFile.downloadHandler.data != null)
                    {
                        this.mBytes = this.mUnityWebRequestFile.downloadHandler.data;
                    }
                    else if (this.mUnityWebRequestFile.downloadHandler.text != null)
                    {
                        this.mText = this.mUnityWebRequestFile.downloadHandler.text;
                    }
                }

                if (this.mIsWriteFile)
                {
                    this.writeFile();
                }
            }

            this.mRefCountResLoadResultNotify.getResLoadState().setSuccessLoaded();
        }
        else
        {
            // UnityWebRequest::responseCode 如果不是 200 ，this.mUnityWebRequestFile.downloadHandler.text 中内容是错误描述
            if (null != this.mUnityWebRequestFile.downloadHandler &&
               !UtilStr.isNullOrEmpty(this.mUnityWebRequestFile.downloadHandler.text))
            {
                this.mErrorStr = this.mUnityWebRequestFile.downloadHandler.text;
            }

            this.mRefCountResLoadResultNotify.getResLoadState().setFailed();

            if (MacroDef.ENABLE_LOG)
            {
                if (null != this.mUnityWebRequestFile)
                {
                    Ctx.mInstance.mLogSys.log(String.format("WebRequestDownloadItem::onWWWEnd, fail, www not null, path = %s errorstr = %s", this.mLoadPath, this.mErrorStr), LogTypeId.eLogDownload);
                }
                else
                {
                    Ctx.mInstance.mLogSys.log(String.format("WebRequestDownloadItem::onWWWEnd, fail, www is null, path = %s errorstr = %s", this.mLoadPath, this.mErrorStr), LogTypeId.eLogDownload);
                }
            }
        }

        this.mRefCountResLoadResultNotify.getLoadResEventDispatch().dispatchEvent(this);
    }
}