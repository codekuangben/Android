package SDK.Lib.EventHandle;

import SDK.Lib.DelayHandle.IDelayHandleItem;
import SDK.Lib.Tools.UtilApi;

public class EventDispatchFunctionObject implements IDelayHandleItem
{
    public boolean mIsClientDispose;       // 是否释放了资源
    public ICalleeObject mThis;
    public IDispatchObject mHandle;

    public EventDispatchFunctionObject()
    {
        this.mIsClientDispose = false;
    }

    public void setFuncObject(ICalleeObject pThis, IDispatchObject func)
    {
        this.mThis = pThis;
        this.mHandle = func;
    }

    public boolean isValid()
    {
        return this.mThis != null || this.mHandle != null;
    }

    public boolean isEqual(ICalleeObject pThis, IDispatchObject handle)
    {
        boolean ret = false;
        if(pThis != null)
        {
            ret = UtilApi.isAddressEqual(this.mThis, pThis);
            if (!ret)
            {
                return ret;
            }
        }
        if (handle != null)
        {
            //ret = UtilApi.isAddressEqual(this.mHandle, handle);
            ret = UtilApi.isDelegateEqual(this.mHandle, handle);
            if (!ret)
            {
                return ret;
            }
        }

        return ret;
    }

    public void call(IDispatchObject dispObj)
    {
        if(mThis != null)
        {
            mThis.call(dispObj);
        }

//        if(null != this.mHandle)
//        {
//            this.mHandle(dispObj);
//        }
    }

    public void setClientDispose(boolean isDispose)
    {
        this.mIsClientDispose = isDispose;
    }

    public boolean isClientDispose()
    {
        return this.mIsClientDispose;
    }
}