package SDK.Lib.Resource.Download;

import SDK.Lib.Core.UniqueNumIdGen;
import SDK.Lib.DataStruct.MList;
import SDK.Lib.EventHandle.IDispatchObject;
import SDK.Lib.FrameHandle.LoopDepth;
import SDK.Lib.FrameWork.Ctx;
import SDK.Lib.FrameWork.MacroDef;
import SDK.Lib.Log.LogTypeId;
import SDK.Lib.MsgRoute.LoadedWebResMR;
import SDK.Lib.MsgRoute.MsgRouteBase;
import SDK.Lib.MsgRoute.MsgRouteHandleBase;
import SDK.Lib.MsgRoute.MsgRouteID;
import SDK.Lib.Resource.ResMsgRoute.ResMsgRouteCB;

/**
 * @brief 数据下载管理器
 */
public class DownloadMgr extends MsgRouteHandleBase
{
    protected int mMaxParral;              // 最多同时加载的内容
    protected int mCurNum;                 // 当前加载的数量
    protected DownloadData mLoadData;
    protected DownloadItem mRetLoadItem;
    protected ResMsgRouteCB mResMsgRouteCB;
    protected MList<String> mZeroRefResIDList;   // 没有引用的资源 ID 列表
    protected LoopDepth mLoopDepth;                // 加载深度

    public UniqueNumIdGen mUniqueNumIdGen;

    public DownloadMgr()
    {
        this.mMaxParral = 8;
        this.mCurNum = 0;
        this.mLoadData = new DownloadData();
        this.mZeroRefResIDList = new MList<String>();

        this.mLoopDepth = new LoopDepth();
        this.mLoopDepth.setZeroHandle(null, this.unloadNoRefResFromList);

        this.mUniqueNumIdGen = new UniqueNumIdGen(0);
        this.addMsgRouteHandle(MsgRouteID.eMRIDLoadedWebRes, this.onMsgRouteResLoad);
    }

    @Override
    public void init()
    {
        // 游戏逻辑处理
        this.mResMsgRouteCB = new ResMsgRouteCB();
        Ctx.mInstance.mMsgRouteNotify.addOneNofity(this.mResMsgRouteCB);
    }

    @Override
    public void dispose()
    {

    }

    protected void resetLoadParam(DownloadParam loadParam)
    {
        loadParam.reset();
    }

    // 是否有正在加载的 DownloadItem
    public boolean hasDownloadItem(String resUniqueId)
    {
        boolean ret = false;
        boolean isFind = false;

        for(DownloadItem loadItem : this.mLoadData.mPath2LoadItem.Values)
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
            for(DownloadItem loadItem : this.mLoadData.mWillLoadItem.list())
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
    public boolean isDownloaded(String path)
    {
        boolean ret = false;

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

    public boolean isSuccessDownLoaded(String resUniqueId)
    {
        boolean ret = false;

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

    public DownloadItem getDownloadItem(String resUniqueId)
    {
        DownloadItem downloadItem = null;
        boolean isFind = false;

        for(DownloadItem loadItem : this.mLoadData.mPath2LoadItem.Values)
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
            for(DownloadItem loadItem : this.mLoadData.mWillLoadItem.list())
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
        this.mLoadData.mPath2LoadItem.get(param.mResUniqueId).getRefCountResLoadResultNotify().getRefCount().incRef();

        if (this.mLoadData.mPath2LoadItem.get(param.mResUniqueId).getRefCountResLoadResultNotify().getResLoadState().hasLoaded())
        {
            if (param.mLoadEventHandle != null)
            {
                // 这个回调函数可能会调用 unload 卸载资源
                param.mLoadEventHandle(this.mLoadData.mPath2LoadItem.get(param.mResUniqueId), 0);
            }
        }
        else
        {
            if (param.mLoadEventHandle != null)
            {
                this.mLoadData.mPath2LoadItem.get(param.mResUniqueId).getAllLoadResEventDispatch().addEventHandle(null, param.mLoadEventHandle);

                if(MacroDef.ENABLE_LOG)
                {
                    if(this.mLoadData.mPath2LoadItem.get(param.mResUniqueId).getAllLoadResEventDispatch().getEventHandleCount() > 1)
                    {
                        Ctx.mInstance.mLogSys.log(String.format("DownloadMgr::downloadWithDownloading, has multiple event handle, ResUniqueId = %s", param.mResUniqueId), LogTypeId.eLogResLoader);
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
                    Ctx.mInstance.mLogSys.log(String.format("DownloadMgr::downloadWithNotDownload, start, path = %s", param.mLoadPath), LogTypeId.eLogDownload);
                }

                // 先增加，否则退出的时候可能是先减 1 ，导致越界出现很大的值
                ++this.mCurNum;
                this.mLoadData.mPath2LoadItem.set(param.mResUniqueId, loadItem);
                this.mLoadData.mPath2LoadItem.get(param.mResUniqueId).load();
            }
            else
            {
                if (MacroDef.ENABLE_LOG)
                {
                    Ctx.mInstance.mLogSys.log(String.format("DownloadMgr::downloadWithNotDownload, queue, path = %s, CurNum = %s, MaxParral = {2}", param.mLoadPath, this.mCurNum, this.mMaxParral), LogTypeId.eLogDownload);
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
    public void unload(String resUniqueId, MEventDispatchAction<IDispatchObject> loadEventHandle)
    {
        if (this.mLoadData.mPath2LoadItem.ContainsKey(resUniqueId))
        {
            // 移除事件监听器，因为很有可能移除的时候，资源还没加载完成，这个时候事件监听器中的处理函数列表还没有清理
            this.mLoadData.mPath2LoadItem.get(resUniqueId).getRefCountResLoadResultNotify().getLoadResEventDispatch().removeEventHandle(null, loadEventHandle);
            this.mLoadData.mPath2LoadItem.get(resUniqueId).getRefCountResLoadResultNotify().getRefCount().decRef();

            if (this.mLoadData.mPath2LoadItem.get(resUniqueId).getRefCountResLoadResultNotify().getRefCount().isNoRef())
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
        MList<String> resUniqueIdList = new MList<String>();

        for(String resUniqueId : this.mLoadData.mPath2LoadItem.Keys)
        {
            resUniqueIdList.Add(resUniqueId);
        }

        int idx = 0;
        int len = resUniqueIdList.length();
        while (idx < len)
        {
            this.unloadNoRefRes(resUniqueIdList.get(idx));
            ++idx;
        }

        resUniqueIdList.Clear();
        resUniqueIdList = null;

        this.mLoadData.mWillLoadItem.Clear();
    }

    // 添加无引用资源到 List
    protected void addNoRefResID2List(String resUniqueId)
    {
        this.mZeroRefResIDList.Add(resUniqueId);
    }

    // 卸载没有引用的资源列表中的资源
    protected void unloadNoRefResFromList()
    {
        for(String path : this.mZeroRefResIDList.list())
        {
            if (this.mLoadData.mPath2LoadItem.ContainsKey(path))
            {
                if (this.mLoadData.mPath2LoadItem.get(path).getRefCountResLoadResultNotify().getRefCount().isNoRef())
                {
                    this.unloadNoRefRes(path);
                }
            }
            else
            {
                if (MacroDef.ENABLE_LOG)
                {
                    Ctx.mInstance.mLogSys.log(String.format("DownloadMgr::unloadNoRefResFromList, fail, path = %s", path), LogTypeId.eLogDownload);
                }
            }
        }

        this.mZeroRefResIDList.Clear();
    }

    // 不考虑引用计数，直接卸载
    protected void unloadNoRefRes(String resUniqueId)
    {
        // 如果在加载队列中
        if (this.mLoadData.mPath2LoadItem.ContainsKey(resUniqueId))
        {
            this.mLoadData.mPath2LoadItem.get(resUniqueId).unload();
            this.mLoadData.mPath2LoadItem.get(resUniqueId).reset();
            this.mLoadData.mNoUsedLoadItem.Add(this.mLoadData.mPath2LoadItem.get(resUniqueId));
            this.mLoadData.mPath2LoadItem.Remove(resUniqueId);
        }
        else
        {
            // 检查是否存在排队加载的的，如果存在就直接移除
            this.removeWillLoadItem(resUniqueId);

            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log(String.format("DownloadMgr::unloadNoRefRes, cannot find, resUniqueId = %s", resUniqueId), LogTypeId.eLogDownload);
            }
        }
    }

    public void removeWillLoadItem(String resUniqueId)
    {
        for(DownloadItem loadItem : this.mLoadData.mWillLoadItem.list())
        {
            if (loadItem.getResUniqueId() == resUniqueId)
            {
                this.releaseLoadItem(loadItem);      // 必然只有一个，如果有多个就是错误
                break;
            }
        }
    }

    public void onLoadEventHandle(IDispatchObject dispObj, int uniqueId)
    {
        this.mLoopDepth.incDepth();

        DownloadItem item = (DownloadItem)dispObj;
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
            this.mLoadData.mPath2LoadItem.get(item.getResUniqueId()).init();
        }
        else        // 如果资源已经没有使用的地方了
        {
            item.unload();          // 直接卸载掉
        }
    }

    public void onFailed(DownloadItem item)
    {
        String resUniqueId = item.getResUniqueId();

        if (this.mLoadData.mPath2LoadItem.ContainsKey(resUniqueId))
        {
            this.mLoadData.mPath2LoadItem.get(resUniqueId).failed();
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
                Ctx.mInstance.mLogSys.log(String.format("DownloadMgr::releaseLoadItem, add NoUsedLDItem multiple, resUniqueId = %s", item.getResUniqueId()), LogTypeId.eLogDownload);
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
                String resUniqueId = ((DownloadItem)this.mLoadData.mWillLoadItem.get(0)).getResUniqueId();
                this.mLoadData.mPath2LoadItem.set(resUniqueId, (DownloadItem)this.mLoadData.mWillLoadItem.get(0));
                this.mLoadData.mWillLoadItem.RemoveAt(0);
                this.mLoadData.mPath2LoadItem.get(resUniqueId).load();

                ++this.mCurNum;

                if (MacroDef.ENABLE_LOG)
                {
                    Ctx.mInstance.mLogSys.log(String.format("DownloadMgr::loadNextItem, load, path = %s, CurNum = %s, MaxParral = %s", this.mLoadData.mPath2LoadItem.get(resUniqueId).getLoadPath(), this.mCurNum, this.mMaxParral), LogTypeId.eLogDownload);
                }
            }
        }
    }

    protected DownloadItem findDownloadItemFormPool()
    {
        this.mRetLoadItem = null;

        for(DownloadItem item : this.mLoadData.mNoUsedLoadItem.list())
        {
            this.mRetLoadItem = item;
            this.mLoadData.mNoUsedLoadItem.Remove(this.mRetLoadItem);
            break;
        }

        return this.mRetLoadItem;
    }

    // 资源加载完成，触发下一次加载
    protected void onMsgRouteResLoad(IDispatchObject dispObj, int uniqueId)
    {
        MsgRouteBase msg = (MsgRouteBase)dispObj;
        DownloadItem loadItem = (DownloadItem)(((LoadedWebResMR)msg).mTask);
        loadItem.handleResult();
    }
}