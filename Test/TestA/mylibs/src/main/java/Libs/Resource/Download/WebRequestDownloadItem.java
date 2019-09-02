package Libs.Resource.Download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import Libs.FrameWork.Ctx;
import Libs.FrameWork.MacroDef;
import Libs.Log.LogTypeId;
import Libs.Resource.ResLoadData.ResPackType;
import Libs.Tools.UtilAndroidWrap;
import Libs.Tools.UtilStr;
import Libs.Resource.Download.DownloadItem;

/**
* @brief Java 中使用HttpURLConnection发起POST 请求
* @ref http://blog.csdn.net/miemie1320/article/details/23029439
*/
public class WebRequestDownloadItem extends DownloadItem
{
    protected HttpURLConnection mUnityWebRequestFile;
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
            this.mUnityWebRequestFile.disconnect();
            this.mUnityWebRequestFile = null;
        }
    }

    @Override
    public HttpURLConnection getUnityWebRequestFile()
    {
        return this.mUnityWebRequestFile;
    }

    @Override
    public void load()
    {
        super.load();

        // 通过线程任务队列下载
        //Ctx.mInstance.mCoroutineMgr.StartCoroutine(this.downloadAsset());
    }

    // mPath 是这个格式 http://127.0.0.1/UnityServer/Version.txt?ver=100
    @Override
    protected void downloadAsset()
    {
        try
        {
            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log(String.format("WebRequestDownloadItem::downloadAsset, download AssetBundles, DownloadNoVerPath = %s", this.mDownloadNoVerPath), LogTypeId.eLogDownload);
            }

            URL url = new URL(this.mDownloadVerPath);
            this.mUnityWebRequestFile = (HttpURLConnection)url.openConnection();
            // 设定请求的方法，默认是GET
            this.mUnityWebRequestFile.setRequestMethod("POST");
            // 设置字符编码
            this.mUnityWebRequestFile.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            this.mUnityWebRequestFile.connect();
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (null != this.mUnityWebRequestFile)
            {
                this.mUnityWebRequestFile.disconnect();
                this.mUnityWebRequestFile = null;
            }
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (null != this.mUnityWebRequestFile)
            {
                this.mUnityWebRequestFile.disconnect();
                this.mUnityWebRequestFile = null;
            }
        }
        finally
        {
            if (null != this.mUnityWebRequestFile)
            {
                this.mUnityWebRequestFile.disconnect();
                this.mUnityWebRequestFile = null;
            }
        }

        if (MacroDef.ENABLE_LOG)
        {
            if (null != this.mUnityWebRequestFile)
            {
                Ctx.mInstance.mLogSys.log(String.format("WebRequestDownloadItem::downloadAsset, front, www not null, path = %s", this.mLoadPath), LogTypeId.eLogDownload);
                this.logInfo();
            }
            else
            {
                Ctx.mInstance.mLogSys.log(String.format("WebRequestDownloadItem::downloadAsset, front, www is null, path = %s", this.mLoadPath), LogTypeId.eLogDownload);
                this.logInfo();
            }
        }

        if (MacroDef.ENABLE_LOG)
        {
            if (null != this.mUnityWebRequestFile)
            {
                Ctx.mInstance.mLogSys.log(String.format("WebRequestDownloadItem::downloadAsset, back, www not null, path = %s", this.mLoadPath), LogTypeId.eLogDownload);
                this.logInfo();
            }
            else
            {
                Ctx.mInstance.mLogSys.log(String.format("WebRequestDownloadItem::downloadAsset, back, www is null, path = %s", this.mLoadPath), LogTypeId.eLogDownload);
                this.logInfo();
            }
        }

        this.onWWWEnd();
    }

    // 加载完成回调处理
    @Override
    protected void onWWWEnd()
    {
        if (null != this.mUnityWebRequestFile)
        {
            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log(String.format("DownloadItem::onWWWEnd, success, path = %s", this.mLoadPath), LogTypeId.eLogDownload);
            }

            try
            {
                // 文件大小
                int fileLength = this.mUnityWebRequestFile.getContentLength();

                // 文件名
                String filePathUrl = this.mUnityWebRequestFile.getURL().getFile();
                String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

                BufferedInputStream bin = new BufferedInputStream(this.mUnityWebRequestFile.getInputStream());

                this.mBytes = new byte[fileLength];
                bin.read(this.mBytes);
            }
            catch (IOException err)
            {
                err.printStackTrace();
            }

            if (this.mIsWriteFile)
            {
                this.writeFile();
            }

            this.mRefCountResLoadResultNotify.getResLoadState().setSuccessLoaded();
        }
        else
        {
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