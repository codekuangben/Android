package com.kb.mylibs.EventHandle;

import com.kb.mylibs.DelayHandle.IDelayHandleItem;
import com.kb.mylibs.Tools.UtilSysLibsWrap;

public class EventDispatchFunctionObject implements IDelayHandleItem
{
    public boolean mIsClientDispose;       // 是否释放了资源
    public IEventListener mEventListener;
    public IDispatchObject mEventHandle;
    public int mEventId;

    public EventDispatchFunctionObject()
    {
        this.mIsClientDispose = false;
    }

    public void setFuncObject(
            IEventListener eventListener,
            IDispatchObject eventHandle,
            int eventId
    )
    {
        this.mEventListener = eventListener;
        this.mEventHandle = eventHandle;
        this.mEventId = eventId;
    }

    public boolean isValid()
    {
        return this.mEventListener != null || this.mEventHandle != null;
    }

    public boolean isEqual(
            IEventListener eventListener,
            IDispatchObject eventHandle,
            int eventId
    )
    {
        boolean ret = false;

        if(eventListener != null)
        {
            ret = UtilSysLibsWrap.isAddressEqual(this.mEventListener, eventListener);

            if (!ret)
            {
                return ret;
            }
        }
        if (eventHandle != null)
        {
            //ret = UtilSysLibsWrap.isAddressEqual(this.mEventHandle, eventHandle);
            ret = UtilSysLibsWrap.isDelegateEqual(this.mEventHandle, eventHandle);

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

    public void call(IDispatchObject dispatchObject)
    {
        if(this.mEventListener != null)
        {
            this.mEventListener.call(dispatchObject);
        }

//        if(null != this.mEventHandle)
//        {
//            this.mEventHandle(dispatchObject);
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