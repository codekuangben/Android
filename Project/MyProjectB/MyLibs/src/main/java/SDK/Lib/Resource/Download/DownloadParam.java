package SDK.Lib.Resource.Download;

import SDK.Lib.EventHandle.ICalleeObject;
import SDK.Lib.EventHandle.IDispatchObject;
import SDK.Lib.Resource.ResLoadData.ResLoadType;
import SDK.Lib.Resource.ResLoadData.ResPackType;
import SDK.Lib.Tools.UtilApi;
import SDK.Lib.Tools.UtilPath;

/**
 * @brief 下载参数
 */
public class DownloadParam
{
    public String mLoadPath;
    public String mOrigPath;
    public String mResUniqueId;
    public String mExtName;
    public String mVersion;

    public ICalleeObject mLoadEventHandle;
    public ICalleeObject mProgressEventHandle;
    public DownloadType mDownloadType;
    public ResLoadType mResLoadType;
    public ResPackType mResPackType;

    public boolean mIsWriteFile;       // 下载完成是否写入文件
    public long mFileLen;           // 文件长度，如果使用 HttpWeb 下载，使用这个字段判断文件长度
    protected boolean mIsAddVerInLocalFileName;    // 本地文件名字是否添加版本号
    protected boolean mIsNeedUncompress;
    protected String mDownloadURL;  // 下载地址

    public DownloadParam()
    {
        this.reset();
    }

    public void reset()
    {
        this.mResLoadType = ResLoadType.eLoadWeb;
        //this.mDownloadType = DownloadType.eHttpWeb;
        //this.mDownloadType = DownloadType.eWWW;
        this.mDownloadType = DownloadType.eWebRequest;
        this.mIsWriteFile = true;
        this.mFileLen = 0;
        this.mVersion = "";
        this.mIsAddVerInLocalFileName = true;

        this.mLoadEventHandle = null;
        this.mProgressEventHandle = null;
        this.mIsNeedUncompress = false;
        this.mDownloadURL = "";
    }

    public void setPath(String origPath)
    {
        this.mOrigPath = origPath;
        this.mLoadPath = mOrigPath;
        this.mResUniqueId = mOrigPath;
        this.mVersion = "";

        this.mExtName = UtilPath.getFileExt(this.mOrigPath);

        if(this.mExtName == UtilApi.UNITY3D)
        {
            this.mResPackType = ResPackType.eBundleType;
        }
        else
        {
            this.mResPackType = ResPackType.eDataType;
        }
    }

    public void setIsNeedUncompress(boolean value)
    {
        this.mIsNeedUncompress = value;
    }

    public boolean getIsNeedUncompress()
    {
        return this.mIsNeedUncompress;
    }

    public String getDownloadURL()
    {
        return this.mDownloadURL;
    }

    public void setDownloadURL(String value)
    {
        this.mDownloadURL = value;
    }
}