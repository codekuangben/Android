package SDK.Lib.Resource.Download;

/**
* @brief 从网络下载数据
*/
public class DownloadItem : ITask, IDispatchObject, ILoadProgress
{
    protected byte[] mBytes;
    protected string mText;
    protected string mVersion;

    protected string mLocalPath;            // 本地文件系统目录
    protected string mOrigPath;             // 原始的加载目录
    protected string mLoadPath;             // 加载目录
    protected string mResUniqueId;          // 资源 Unique Id
    protected string mDownloadNoVerPath;    // 下载目录，没有版本号
    protected string mDownloadVerPath;      // 下载目录，有版本号

    protected DownloadType mDownloadType;   // 加载类型
    protected ResLoadType mResLoadType;
    protected ResPackType mResPackType;

    protected RefCountResLoadResultNotify mRefCountResLoadResultNotify;
    protected ResEventDispatch mAllLoadResEventDispatch;    // 事件分发器，这个是记录逻辑的事件分发器

    protected bool mIsWriteFile;
    protected long mFileLen;
    protected uint mUniqueId;
    protected bool mIsAddVerInLocalFileName;    // 本地文件名字是否添加版本号
    protected bool mIsNeedUncompress;

    protected string mDownloadURL;

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

    virtual public void reset()
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

        this.mDownloadType = DownloadType.eHttpWeb;
        this.mDownloadURL = "";

        this.mRefCountResLoadResultNotify.getResLoadState().reset();
        this.mRefCountResLoadResultNotify.getRefCount().reset();
        this.mRefCountResLoadResultNotify.getLoadResEventDispatch().clearEventHandle();
    }

    public string getLoadPath()
    {
        return this.mLoadPath;
    }

    public void setLoadPath(string value)
    {
        this.mLoadPath = value;
    }

    public string getOrigPath()
    {
        return this.mOrigPath;
    }

    public void setOrigPath(string value)
    {
        this.mOrigPath = value;
    }

    virtual public WWW getW3File()
    {
        return null;
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

    public bool hasSuccessLoaded()
    {
        return this.mRefCountResLoadResultNotify.getResLoadState().hasSuccessLoaded();
    }

    public bool hasFailed()
    {
        return this.mRefCountResLoadResultNotify.getResLoadState().hasFailed();
    }

    public void setResUniqueId(string value)
    {
        this.mResUniqueId = value;
    }

    public string getResUniqueId()
    {
        return this.mResUniqueId;
    }

    public string getDownloadURL()
    {
        return this.mDownloadURL;
    }

    public void setDownloadURL(string value)
    {
        this.mDownloadURL = value;
    }

    public byte[] getBytes()
    {
        return this.mBytes;
    }

    public string getText()
    {
        return this.mText;
    }

    virtual public void init()
    {

    }

    virtual public void failed()
    {

    }

    // 这个是卸载，因为有时候资源加载进来可能已经不用了，需要直接卸载掉
    virtual public void unload()
    {

    }

    virtual protected IEnumerator downloadAsset()
    {
        yield return null;
    }

    public void setIsNeedUncompress(bool value)
    {
        this.mIsNeedUncompress = value;
    }

    // 加载完成回调处理
    virtual protected void onWWWEnd()
    {

    }

    protected void deleteFromCache(string path)
    {
        if (Caching.IsVersionCached(path, 1))
        {
            Caching.CleanCache();
        }
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

    virtual public void load()
    {
        this.mRefCountResLoadResultNotify.getResLoadState().setLoading();

        this.mLocalPath = UtilPath.combine(MFileSys.getLocalWriteDir(), UtilLogic.getRelPath(this.mLoadPath));

        if (this.mIsAddVerInLocalFileName)
        {
            if (!string.IsNullOrEmpty(this.mVersion))
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

    public virtual void runTask()
    {

    }

    public virtual void handleResult()
    {

    }

    // 加载完成写入本地文件系统
    public virtual void writeFile()
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