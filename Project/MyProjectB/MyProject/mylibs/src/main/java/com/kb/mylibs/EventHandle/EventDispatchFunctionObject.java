package com.kb.mylibs.EventHandle;

import com.kb.mylibs.DelayHandle.IDelayHandleItem;
import com.kb.mylibs.Tools.UtilSysLibsWrap;

public class EventDispatchFunctionObject implements IDelayHandleItem
{
    public boolean mIsClientDispose;       // 是否释放了资源
    public ICalleeObject mThis;
    public IDispatchObject mHandle;
    public int mEventId;

    public EventDispatchFunctionObject()
    {
        this.mIsClientDispose = false;
    }

    public void setFuncObject(
            ICalleeObject pThis,
            IDispatchObject func,
            int eventId
    )
    {
        this.mThis = pThis;
        this.mHandle = func;
        this.mEventId = eventId;
    }

    public boolean isValid()
    {
        return this.mThis != null || this.mHandle != null;
    }

    public boolean isEqual(
            ICalleeObject pThis,
            IDispatchObject handle,
            int eventId
    )
    {
        boolean ret = false;

        if(pThis != null)
        {
            ret = UtilSysLibsWrap.isAddressEqual(this.mThis, pThis);

            if (!ret)
            {
                return ret;
            }
        }
        if (handle != null)
        {
            //ret = UtilSysLibsWrap.isAddressEqual(this.mHandle, handle);
            ret = UtilSysLibsWrap.isDelegateEqual(this.mHandle, handle);

            if (!ret)
            {
                return ret;
            }
        }

        if(this.mEventId != eventId)
        {
            return ret;
        }

        return ret;
    }

    public void call(IDispatchObject dispObj)
    {
        if(this.mThis != null)
        {
            this.mThis.call(dispObj);
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