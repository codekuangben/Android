package Libs.Resource.RefAsync;

import Libs.EventHandle.IDispatchObject;
import Libs.EventHandle.ResEventDispatch;
import Libs.Resource.ResLoadData.ResLoadState;

/**
 * @brief 非引用计数资源加载结果通知
 */
public class ResLoadResultNotify
{
    protected ResLoadState mResLoadState;          // 资源加载状态
    protected ResEventDispatch mLoadResEventDispatch;      // 事件分发器

    public ResLoadResultNotify()
    {
        this.mResLoadState = new ResLoadState();
        this.mLoadResEventDispatch = new ResEventDispatch();
    }

    public void init()
    {

    }

    public void dispose()
    {
        if(null != this.mLoadResEventDispatch)
        {
            this.mLoadResEventDispatch.dispose();
            this.mLoadResEventDispatch = null;
        }
    }

    public ResLoadState getResLoadState()
    {
        return this.mResLoadState;
    }

    public void setResLoadState(ResLoadState value)
    {
        this.mResLoadState = value;
    }

    public ResEventDispatch getLoadResEventDispatch()
    {
        return this.mLoadResEventDispatch;
    }

    public void setLoadResEventDispatch(ResEventDispatch value)
    {
        this.mLoadResEventDispatch = value;
    }

    public void onLoadEventHandle(IDispatchObject dispObj, int uniqueId)
    {
        this.mLoadResEventDispatch.dispatchEvent(dispObj);
        this.mLoadResEventDispatch.clearEventHandle();
    }

    public void copyFrom(ResLoadResultNotify rhv)
    {
        this.mResLoadState.copyFrom(rhv.getResLoadState());
        this.mLoadResEventDispatch = rhv.getLoadResEventDispatch();
    }
}