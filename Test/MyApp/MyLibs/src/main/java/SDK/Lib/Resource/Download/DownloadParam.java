namespace SDK.Lib
{
    /**
     * @brief 下载参数
     */
    public class DownloadParam
    {
        public string mLoadPath;
        public string mOrigPath;
        public string mResUniqueId;
        public string mExtName;
        public string mVersion;

        public MEventDispatchAction<IDispatchObject> mLoadEventHandle;
        public MEventDispatchAction<IDispatchObject> mProgressEventHandle;
        public DownloadType mDownloadType;
        public ResLoadType mResLoadType;
        public ResPackType mResPackType;

        public LuaInterface.LuaTable mLuaTable;
        public LuaInterface.LuaFunction mLuaFunction;
        public bool mIsWriteFile;       // 下载完成是否写入文件
        public long mFileLen;           // 文件长度，如果使用 HttpWeb 下载，使用这个字段判断文件长度
        protected bool mIsAddVerInLocalFileName;    // 本地文件名字是否添加版本号
        protected bool mIsNeedUncompress;
        protected string mDownloadURL;  // 下载地址

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

        public void setPath(string origPath)
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

        public void setIsNeedUncompress(bool value)
        {
            this.mIsNeedUncompress = value;
        }

        public bool getIsNeedUncompress()
        {
            return this.mIsNeedUncompress;
        }

        public string getDownloadURL()
        {
            return this.mDownloadURL;
        }

        public void setDownloadURL(string value)
        {
            this.mDownloadURL = value;
        }
    }
}