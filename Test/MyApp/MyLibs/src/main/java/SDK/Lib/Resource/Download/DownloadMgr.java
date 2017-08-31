using System.Collections.Generic;

namespace SDK.Lib
{
    /**
     * @brief 数据下载管理器
     */
    public class DownloadMgr : MsgRouteHandleBase
    {
        protected uint mMaxParral;              // 最多同时加载的内容
        protected uint mCurNum;                 // 当前加载的数量
        protected DownloadData mLoadData;
        protected DownloadItem mRetLoadItem;
        protected ResMsgRouteCB mResMsgRouteCB;
        protected List<string> mZeroRefResIDList;   // 没有引用的资源 ID 列表
        protected LoopDepth mLoopDepth;                // 加载深度

        public UniqueNumIdGen mUniqueNumIdGen;

        public DownloadMgr()
        {
            this.mMaxParral = 8;
            this.mCurNum = 0;
            this.mLoadData = new DownloadData();
            this.mZeroRefResIDList = new List<string>();

            this.mLoopDepth = new LoopDepth();
            this.mLoopDepth.setZeroHandle(null, this.unloadNoRefResFromList);

            this.mUniqueNumIdGen = new UniqueNumIdGen(0);
            this.addMsgRouteHandle(MsgRouteID.eMRIDLoadedWebRes, this.onMsgRouteResLoad);
        }

        override public void init()
        {
            // 游戏逻辑处理
            this.mResMsgRouteCB = new ResMsgRouteCB();
            Ctx.mInstance.mMsgRouteNotify.addOneNofity(this.mResMsgRouteCB);
        }

        override public void dispose()
        {

        }

        protected void resetLoadParam(DownloadParam loadParam)
        {
            loadParam.reset();
        }

        // 是否有正在加载的 DownloadItem
        public bool hasDownloadItem(string resUniqueId)
        {
            bool ret = false;
            bool isFind = false;

            foreach (DownloadItem loadItem in this.mLoadData.mPath2LoadItem.Values)
            {
                if (loadItem.getResUniqueId() == resUniqueId)
                {
                    ret = true;
                    isFind = true;
                    break;
                }
            }

            if (!isFind)
            {
                foreach (DownloadItem loadItem in this.mLoadData.mWillLoadItem.list())
                {
                    if (loadItem.getResUniqueId() == resUniqueId)
                    {
                        ret = true;
                        isFind = true;
                        break;
                    }
                }
            }

            return ret;
        }

        // 重置加载设置
        protected void resetDownloadParam(DownloadParam loadParam)
        {
            loadParam.reset();
        }

        // 资源是否已经加载，包括成功和失败
        public bool isDownloaded(string path)
        {
            bool ret = false;

            DownloadItem downloadItem = this.getDownloadItem(path);

            if (downloadItem == null)
            {
                ret = false;
            }
            else if (downloadItem.getRefCountResLoadResultNotify().getResLoadState().hasSuccessLoaded() ||
                     downloadItem.getRefCountResLoadResultNotify().getResLoadState().hasFailed())
            {
                ret = true;
            }

            return ret;
        }

        public bool isSuccessDownLoaded(string resUniqueId)
        {
            bool ret = false;

            DownloadItem downloadItem = this.getDownloadItem(resUniqueId);

            if (downloadItem == null)
            {
                ret = false;
            }
            else if (downloadItem.getRefCountResLoadResultNotify().getResLoadState().hasSuccessLoaded())
            {
                ret = true;
            }

            return ret;
        }

        public DownloadItem getDownloadItem(string resUniqueId)
        {
            DownloadItem downloadItem = null;
            bool isFind = false;

            foreach (DownloadItem loadItem in this.mLoadData.mPath2LoadItem.Values)
            {
                if (loadItem.getResUniqueId() == resUniqueId)
                {
                    downloadItem = loadItem;
                    isFind = true;
                    break;
                }
            }

            if (!isFind)
            {
                foreach (DownloadItem loadItem in this.mLoadData.mWillLoadItem.list())
                {
                    if (loadItem.getResUniqueId() == resUniqueId)
                    {
                        downloadItem = loadItem;
                        isFind = true;
                        break;
                    }
                }
            }

            return downloadItem;
        }

        protected DownloadItem createDownloadItem(DownloadParam param)
        {
            DownloadItem loadItem = this.findDownloadItemFormPool();

            if (loadItem == null)
            {
                if (param.mDownloadType == DownloadType.eWWW)
                {
                    loadItem = new WWWDownloadItem();
                }
                else if (param.mDownloadType == DownloadType.eHttpWeb)
                {
                    loadItem = new HttpWebDownloadItem();
                }
                else if (param.mDownloadType == DownloadType.eWebRequest)
                {
                    loadItem = new WebRequestDownloadItem();
                }
            }

            loadItem.getRefCountResLoadResultNotify().getRefCount().incRef();
            loadItem.setLoadParam(param);
            loadItem.getRefCountResLoadResultNotify().getLoadResEventDispatch().addEventHandle(null, this.onLoadEventHandle);
            loadItem.getAllLoadResEventDispatch().addEventHandle(null, param.mLoadEventHandle);

            return loadItem;
        }

        protected void downloadWithDownloading(DownloadParam param)
        {
            this.mLoadData.mPath2LoadItem[param.mResUniqueId].getRefCountResLoadResultNotify().getRefCount().incRef();

            if (this.mLoadData.mPath2LoadItem[param.mResUniqueId].getRefCountResLoadResultNotify().getResLoadState().hasLoaded())
            {
                if (param.mLoadEventHandle != null)
                {
                    // 这个回调函数可能会调用 unload 卸载资源
                    param.mLoadEventHandle(this.mLoadData.mPath2LoadItem[param.mResUniqueId], 0);
                }
            }
            else
            {
                if (param.mLoadEventHandle != null)
                {
                    this.mLoadData.mPath2LoadItem[param.mResUniqueId].getAllLoadResEventDispatch().addEventHandle(null, param.mLoadEventHandle);

                    if(MacroDef.ENABLE_LOG)
                    {
                        if(this.mLoadData.mPath2LoadItem[param.mResUniqueId].getAllLoadResEventDispatch().getEventHandleCount() > 1)
                        {
                            Ctx.mInstance.mLogSys.log(string.Format("DownloadMgr::downloadWithDownloading, has multiple event handle, ResUniqueId = {0}", param.mResUniqueId), LogTypeId.eLogResLoader);
                        }
                    }
                }
            }

            this.resetLoadParam(param);
        }

        protected void downloadWithNotDownload(DownloadParam param)
        {
            if (!this.hasDownloadItem(param.mResUniqueId))
            {
                DownloadItem loadItem = this.createDownloadItem(param);

                if (this.mCurNum < this.mMaxParral)
                {
                    if (MacroDef.ENABLE_LOG)
                    {
                        Ctx.mInstance.mLogSys.log(string.Format("DownloadMgr::downloadWithNotDownload, start, path = {0}", param.mLoadPath), LogTypeId.eLogDownload);
                    }

                    // 先增加，否则退出的时候可能是先减 1 ，导致越界出现很大的值
                    ++this.mCurNum;
                    this.mLoadData.mPath2LoadItem[param.mResUniqueId] = loadItem;
                    this.mLoadData.mPath2LoadItem[param.mResUniqueId].load();
                }
                else
                {
                    if (MacroDef.ENABLE_LOG)
                    {
                        Ctx.mInstance.mLogSys.log(string.Format("DownloadMgr::downloadWithNotDownload, queue, path = {0}, CurNum = {1}, MaxParral = {2}", param.mLoadPath, this.mCurNum, this.mMaxParral), LogTypeId.eLogDownload);
                    }

                    this.mLoadData.mWillLoadItem.Add(loadItem);
                }
            }

            this.resetLoadParam(param);
        }

        // 通用类型，需要自己设置很多参数
        public void download(DownloadParam param)
        {
            this.mLoopDepth.incDepth();

            if (this.mLoadData.mPath2LoadItem.ContainsKey(param.mResUniqueId))
            {
                this.downloadWithDownloading(param);
            }
            else
            {
                this.downloadWithNotDownload(param);
            }

            this.mLoopDepth.decDepth();
        }

        public DownloadItem getAndDownload(DownloadParam param)
        {
            this.download(param);
            return this.getDownloadItem(param.mResUniqueId);
        }

        // 这个卸载有引用计数，如果有引用计数就卸载不了
        public void unload(string resUniqueId, MEventDispatchAction<IDispatchObject> loadEventHandle)
        {
            if (this.mLoadData.mPath2LoadItem.ContainsKey(resUniqueId))
            {
                // 移除事件监听器，因为很有可能移除的时候，资源还没加载完成，这个时候事件监听器中的处理函数列表还没有清理
                this.mLoadData.mPath2LoadItem[resUniqueId].getRefCountResLoadResultNotify().getLoadResEventDispatch().removeEventHandle(null, loadEventHandle);
                this.mLoadData.mPath2LoadItem[resUniqueId].getRefCountResLoadResultNotify().getRefCount().decRef();

                if (this.mLoadData.mPath2LoadItem[resUniqueId].getRefCountResLoadResultNotify().getRefCount().isNoRef())
                {
                    if (this.mLoopDepth.isInDepth())
                    {
                        this.addNoRefResID2List(resUniqueId);
                    }
                    else
                    {
                        this.unloadNoRefRes(resUniqueId);
                    }
                }
            }
        }

        // 卸载所有的资源
        public void unloadAll()
        {
            MList<string> resUniqueIdList = new MList<string>();

            foreach (string resUniqueId in this.mLoadData.mPath2LoadItem.Keys)
            {
                resUniqueIdList.Add(resUniqueId);
            }

            int idx = 0;
            int len = resUniqueIdList.length();
            while (idx < len)
            {
                this.unloadNoRefRes(resUniqueIdList[idx]);
                ++idx;
            }

            resUniqueIdList.Clear();
            resUniqueIdList = null;

            this.mLoadData.mWillLoadItem.Clear();
        }

        // 添加无引用资源到 List
        protected void addNoRefResID2List(string resUniqueId)
        {
            this.mZeroRefResIDList.Add(resUniqueId);
        }

        // 卸载没有引用的资源列表中的资源
        protected void unloadNoRefResFromList()
        {
            foreach (string path in this.mZeroRefResIDList)
            {
                if (this.mLoadData.mPath2LoadItem.ContainsKey(path))
                {
                    if (this.mLoadData.mPath2LoadItem[path].getRefCountResLoadResultNotify().getRefCount().isNoRef())
                    {
                        this.unloadNoRefRes(path);
                    }
                }
                else
                {
                    if (MacroDef.ENABLE_LOG)
                    {
                        Ctx.mInstance.mLogSys.log(string.Format("DownloadMgr::unloadNoRefResFromList, fail, path = {0}", path), LogTypeId.eLogDownload);
                    }
                }
            }

            this.mZeroRefResIDList.Clear();
        }

        // 不考虑引用计数，直接卸载
        protected void unloadNoRefRes(string resUniqueId)
        {
            // 如果在加载队列中
            if (this.mLoadData.mPath2LoadItem.ContainsKey(resUniqueId))
            {
                this.mLoadData.mPath2LoadItem[resUniqueId].unload();
                this.mLoadData.mPath2LoadItem[resUniqueId].reset();
                this.mLoadData.mNoUsedLoadItem.Add(mLoadData.mPath2LoadItem[resUniqueId]);
                this.mLoadData.mPath2LoadItem.Remove(resUniqueId);
            }
            else
            {
                // 检查是否存在排队加载的的，如果存在就直接移除
                this.removeWillLoadItem(resUniqueId);

                if (MacroDef.ENABLE_LOG)
                {
                    Ctx.mInstance.mLogSys.log(string.Format("DownloadMgr::unloadNoRefRes, cannot find, resUniqueId = {0}", resUniqueId), LogTypeId.eLogDownload);
                }
            }
        }

        public void removeWillLoadItem(string resUniqueId)
        {
            foreach (DownloadItem loadItem in this.mLoadData.mWillLoadItem.list())
            {
                if (loadItem.getResUniqueId() == resUniqueId)
                {
                    this.releaseLoadItem(loadItem);      // 必然只有一个，如果有多个就是错误
                    break;
                }
            }
        }

        public void onLoadEventHandle(IDispatchObject dispObj, uint uniqueId)
        {
            this.mLoopDepth.incDepth();

            DownloadItem item = dispObj as DownloadItem;
            item.getRefCountResLoadResultNotify().getLoadResEventDispatch().removeEventHandle(null, this.onLoadEventHandle);

            if (item.getRefCountResLoadResultNotify().getResLoadState().hasSuccessLoaded())
            {
                this.onLoaded(item);
            }
            else if (item.getRefCountResLoadResultNotify().getResLoadState().hasFailed())
            {
                this.onFailed(item);
            }

            item.getAllLoadResEventDispatch().dispatchEvent(item);

            //this.releaseLoadItem(item);   // 只有卸载的时候才释放，不是 LoadItem,是类似 ResItem
            --this.mCurNum;
            this.loadNextItem();

            this.mLoopDepth.decDepth();
        }

        public void onLoaded(DownloadItem item)
        {
            if (this.mLoadData.mPath2LoadItem.ContainsKey(item.getResUniqueId()))
            {
                this.mLoadData.mPath2LoadItem[item.getResUniqueId()].init();
            }
            else        // 如果资源已经没有使用的地方了
            {
                item.unload();          // 直接卸载掉
            }
        }

        public void onFailed(DownloadItem item)
        {
            string resUniqueId = item.getResUniqueId();

            if (this.mLoadData.mPath2LoadItem.ContainsKey(resUniqueId))
            {
                this.mLoadData.mPath2LoadItem[resUniqueId].failed();
            }
        }

        protected void releaseLoadItem(DownloadItem item)
        {
            item.reset();

            if(!this.mLoadData.mNoUsedLoadItem.Contains(item))
            {
                this.mLoadData.mNoUsedLoadItem.Add(item);
            }
            else
            {
                if (MacroDef.ENABLE_LOG)
                {
                    Ctx.mInstance.mLogSys.log(string.Format("DownloadMgr::releaseLoadItem, add NoUsedLDItem multiple, resUniqueId = {0}", item.getResUniqueId()), LogTypeId.eLogDownload);
                }
            }

            this.mLoadData.mWillLoadItem.Remove(item);
            this.mLoadData.mPath2LoadItem.Remove(item.getResUniqueId());
        }

        protected void loadNextItem()
        {
            if (this.mCurNum < this.mMaxParral)
            {
                if (this.mLoadData.mWillLoadItem.Count() > 0)
                {
                    string resUniqueId = (this.mLoadData.mWillLoadItem[0] as DownloadItem).getResUniqueId();
                    this.mLoadData.mPath2LoadItem[resUniqueId] = this.mLoadData.mWillLoadItem[0] as DownloadItem;
                    this.mLoadData.mWillLoadItem.RemoveAt(0);
                    this.mLoadData.mPath2LoadItem[resUniqueId].load();

                    ++this.mCurNum;

                    if (MacroDef.ENABLE_LOG)
                    {
                        Ctx.mInstance.mLogSys.log(string.Format("DownloadMgr::loadNextItem, load, path = {0}, CurNum = {1}, MaxParral = {2}", this.mLoadData.mPath2LoadItem[resUniqueId].getLoadPath(), this.mCurNum, this.mMaxParral), LogTypeId.eLogDownload);
                    }
                }
            }
        }

        protected DownloadItem findDownloadItemFormPool()
        {
            this.mRetLoadItem = null;

            foreach (DownloadItem item in this.mLoadData.mNoUsedLoadItem.list())
            {
                this.mRetLoadItem = item;
                this.mLoadData.mNoUsedLoadItem.Remove(this.mRetLoadItem);
                break;
            }

            return this.mRetLoadItem;
        }

        // 资源加载完成，触发下一次加载
        protected void onMsgRouteResLoad(IDispatchObject dispObj, uint uniqueId)
        {
            MsgRouteBase msg = dispObj as MsgRouteBase;
            DownloadItem loadItem = (msg as LoadedWebResMR).mTask as DownloadItem;
            loadItem.handleResult();
        }
    }
}