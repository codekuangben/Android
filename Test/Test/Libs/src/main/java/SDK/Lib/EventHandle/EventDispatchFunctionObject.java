package SDK.Lib.EventHandle;

import SDK.Lib.DelayHandle.IDelayHandleItem;
import SDK.Lib.Tools.UtilApi;

public class EventDispatchFunctionObject implements IDelayHandleItem
{
    public boolean mIsClientDispose;       // 是否释放了资源
    public ICalleeObject mThis;
    public IDispatchObject mHandle;
    public int mEventId;   // 事件唯一 Id

    public EventDispatchFunctionObject()
    {
        this.mIsClientDispose = false;
    }

    public void setFuncObject(ICalleeObject pThis, IDispatchObject func, int eventId)
    {
        this.mThis = pThis;
        this.mHandle = func;
        this.mEventId = eventId;
    }

    public boolean isValid()
    {
        return this.mThis != null || this.mHandle != null;
    }

    public boolean isEventIdEqual(int eventId)
    {
        return this.mEventId == eventId;
    }

    public boolean isEqual(ICalleeObject pThis, IDispatchObject handle, int eventId)
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

        if (pThis != null || handle != null)
        {
            ret = this.isEventIdEqual(eventId);

            if (!ret)
            {
                return ret;
            }
        }

        return ret;
    }

    public void call(IDispatchObject dispObj)
    {
        if(this.mThis != null)
        {
            this.mThis.call(dispObj, this.mEventId);
        }

        //if(null != this.mHandle)
        //{
        //    this.mHandle(dispObj);
        //}
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