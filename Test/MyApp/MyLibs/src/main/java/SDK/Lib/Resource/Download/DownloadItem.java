package SDK.Lib.Resource.Download;

import SDK.Lib.EventHandle.IDispatchObject;
import SDK.Lib.EventHandle.ResEventDispatch;
import SDK.Lib.FrameWork.Ctx;
import SDK.Lib.Log.LogTypeId;
import SDK.Lib.Resource.Progress.ILoadProgress;
import SDK.Lib.Resource.RefAsync.RefCountResLoadResultNotify;
import SDK.Lib.Resource.ResLoadData.ResLoadType;
import SDK.Lib.Resource.ResLoadData.ResPackType;
import SDK.Lib.Task.ITask;
import SDK.Lib.Tools.UtilPath;
import SDK.Lib.Tools.UtilStr;

/**
* @brief 从网络下载数据
*/
public class DownloadItem implements ITask, IDispatchObject, ILoadProgress
{
    protected byte[] mBytes;
    protected String mText;
    protected String mVersion;

    protected String mLocalPath;            // 本地文件系统目录
    protected String mOrigPath;             // 原始的加载目录
    protected String mLoadPath;             // 加载目录
    protected String mResUniqueId;          // 资源 Unique Id
    protected String mDownloadNoVerPath;    // 下载目录，没有版本号
    protected String mDownloadVerPath;      // 下载目录，有版本号

    protected DownloadType mDownloadType;   // 加载类型
    protected ResLoadType mResLoadType;
    protected ResPackType mResPackType;

    protected RefCountResLoadResultNotify mRefCountResLoadResultNotify;
    protected ResEventDispatch mAllLoadResEventDispatch;    // 事件分发器，这个是记录逻辑的事件分发器

    protected Boolean mIsWriteFile;
    protected long mFileLen;
    protected int mUniqueId;
    protected Boolean mIsAddVerInLocalFileName;    // 本地文件名字是否添加版本号
    protected Boolean mIsNeedUncompress;

    protected String mDownloadURL;

    public DownloadItem()
    {
        this.mUniqueId = Ctx.mInstance.mDownloadMgr.mUniqueNumIdGen.genNewId();

        this.mVersion = "";
        this.mResLoadType = ResLoadType.eLoadWeb;
        this.mIsAddVerInLocalFileName = false;
        this.mRefCountResLoadResultNotify = new RefCountResLoadResultNotify();
        this.mAllLoadResEventDispatch = new ResEventDispatch();
    }

    public RefCountResLoadResultNotify getRefCountResLoadResultNotify()
    {
        return this.mRefCountResLoadResultNotify;
    }

    public void setRefCountResLoadResultNotify(RefCountResLoadResultNotify value)
    {
        this.mRefCountResLoadResultNotify = value;
    }

    public ResEventDispatch getAllLoadResEventDispatch()
    {
        return this.mAllLoadResEventDispatch;
    }

    public void setAllLoadResEventDispatch(ResEventDispatch value)
    {
        this.mAllLoadResEventDispatch = value;
    }

    public void reset()
    {
        this.mBytes = null;
        this.mText = "";
        this.mVersion = "";

        this.mLocalPath = "";
        this.mLoadPath = "";
        this.mOrigPath = "";
        this.mResUniqueId = "";
        this.mDownloadNoVerPath = "";
        this.mDownloadVerPath = "";

        this.mDownloadType = DownloadType.eWebRequest;
        this.mDownloadURL = "";

        this.mRefCountResLoadResultNotify.getResLoadState().reset();
        this.mRefCountResLoadResultNotify.getRefCount().reset();
        this.mRefCountResLoadResultNotify.getLoadResEventDispatch().clearEventHandle();
    }

    public String getLoadPath()
    {
        return this.mLoadPath;
    }

    public void setLoadPath(String value)
    {
        this.mLoadPath = value;
    }

    public String getOrigPath()
    {
        return this.mOrigPath;
    }

    public void setOrigPath(String value)
    {
        this.mOrigPath = value;
    }

    virtual public UnityWebRequest getUnityWebRequestFile()
    {
        return null;
    }

    public DownloadType getDownloadType()
    {
        return this.mDownloadType;
    }

    public void setDownloadType(DownloadType value)
    {
        this.mDownloadType = value;
    }

    public boolean hasSuccessLoaded()
    {
        return this.mRefCountResLoadResultNotify.getResLoadState().hasSuccessLoaded();
    }

    public boolean hasFailed()
    {
        return this.mRefCountResLoadResultNotify.getResLoadState().hasFailed();
    }

    public void setResUniqueId(String value)
    {
        this.mResUniqueId = value;
    }

    public String getResUniqueId()
    {
        return this.mResUniqueId;
    }

    public String getDownloadURL()
    {
        return this.mDownloadURL;
    }

    public void setDownloadURL(String value)
    {
        this.mDownloadURL = value;
    }

    public byte[] getBytes()
    {
        return this.mBytes;
    }

    public String getText()
    {
        return this.mText;
    }

    public void init()
    {

    }

    public void failed()
    {

    }

    // 这个是卸载，因为有时候资源加载进来可能已经不用了，需要直接卸载掉
    public void unload()
    {

    }

    public void setIsNeedUncompress(boolean value)
    {
        this.mIsNeedUncompress = value;
    }

    public void setLoadParam(DownloadParam param)
    {
        this.setLoadPath(param.mLoadPath);
        this.setOrigPath(param.mOrigPath);
        this.mDownloadType = param.mDownloadType;
        this.mResLoadType = param.mResLoadType;
        this.mResPackType = param.mResPackType;
        this.mVersion = param.mVersion;
        this.mIsWriteFile = param.mIsWriteFile;
        this.mFileLen = param.mFileLen;
        this.setResUniqueId(param.mResUniqueId);
        this.setIsNeedUncompress(param.getIsNeedUncompress());
        this.setDownloadURL(param.getDownloadURL());
    }

    public void load()
    {
        this.mRefCountResLoadResultNotify.getResLoadState().setLoading();

        this.mLocalPath = UtilPath.combine(MFileSys.getLocalWriteDir(), UtilLogic.getRelPath(this.mLoadPath));

        if (this.mIsAddVerInLocalFileName)
        {
            if (!UtilStr.isNullOrEmpty(this.mVersion))
            {
                this.mLocalPath = UtilLogic.combineVerPath(this.mLocalPath, this.mVersion);
            }
        }

        // 这个有 https:// ，因此不能使用 UtilPath.combine，如果使用，会将 // 改成 /
        string url = "";

        if(string.IsNullOrEmpty(this.mDownloadURL))
        {
            url = ResPathResolve.msDataStreamLoadRootPathList[(int)this.mResLoadType];
        }
        else
        {
            url = this.mDownloadURL;
        }

        this.mDownloadNoVerPath = string.Format("{0}/{1}", url, this.mLoadPath);
        this.mDownloadVerPath = string.Format("{0}?ver={1}", this.mDownloadNoVerPath, this.mVersion);

        if(MacroDef.ENABLE_LOG)
        {
            Ctx.mInstance.mLogSys.log(string.Format("DownloadItem::load, LocalPath = {0}, DownloadNoVerPath = {1}, DownloadNoVerPath = {2}", this.mLocalPath, this.mDownloadNoVerPath, this.mDownloadVerPath), LogTypeId.eLogDownload);
        }
    }

    public void runTask()
    {

    }

    public void handleResult()
    {

    }

    // 加载完成写入本地文件系统
    public void writeFile()
    {
        if (this.mBytes != null)
        {
            if (UtilPath.existFile(this.mLocalPath))
            {
                if (MacroDef.ENABLE_LOG)
                {
                    Ctx.mInstance.mLogSys.log(string.Format("DownloadItem::writeFile, existFile, LocalPath = {0}", this.mLocalPath), LogTypeId.eLogDownload);
                }

                UtilPath.deleteFile(this.mLocalPath);
            }
            else
            {
                string path = UtilPath.getFilePathNoName(this.mLocalPath);

                if (!UtilPath.existDirectory(path))
                {
                    if (MacroDef.ENABLE_LOG)
                    {
                        Ctx.mInstance.mLogSys.log(string.Format("DownloadItem::writeFile, NotExistDirectory, Path = {0}", path), LogTypeId.eLogDownload);
                    }

                    UtilPath.createDirectory(path);
                }
            }

            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log(string.Format("DownloadItem::writeFile, writeFile, LocalPath = {0}", this.mLocalPath), LogTypeId.eLogDownload);
            }

            MDataStream dataStream = new MDataStream(this.mLocalPath, null, MFileMode.eCreateNew, MFileAccess.eWrite);
            dataStream.open();

            byte[] outBytes = this.mBytes;
            uint outLen = 0;

            if (this.mIsNeedUncompress)
            {
                Compress.DecompressStrLZMA(this.mBytes, 0, (uint)this.mBytes.Length, ref outBytes, ref outLen);
            }

            dataStream.writeByte(outBytes);
            dataStream.dispose();
            dataStream = null;
            outBytes = null;
        }
    }

    public float getProgress()
    {
        return 0;
    }

    public void logInfo()
    {
        if (MacroDef.ENABLE_LOG)
        {
            Ctx.mInstance.mLogSys.log(string.Format("DownloadItem::logInfo, mLocalPath = {0}, mLoadPath = {1}, mOrigPath = {2}, mResUniqueId = {3}, mDownloadNoVerPath = {4}, mDownloadVerPath = {5}, mResPackType = {6}", this.mLocalPath, this.mLoadPath, this.mOrigPath, this.mResUniqueId, this.mDownloadNoVerPath, this.mDownloadVerPath, this.mResPackType), LogTypeId.eLogDownload);
        }
    }
}