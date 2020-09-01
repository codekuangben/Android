package com.kb.mylibs.FrameHandle;

import com.kb.mylibs.EventHandle.ICalleeObject;
import com.kb.mylibs.Tools.UtilSysLibsWrap;

public class TimerFunctionObject
{
    public ICalleeObjectTimer mEventHandle;

    public TimerFunctionObject()
    {
        this.mEventHandle = null;
    }

    public void setFuncObject(ICalleeObjectTimer eventHandle)
    {
        this.mEventHandle = eventHandle;
    }

    public boolean isValid()
    {
        return this.mEventHandle != null;
    }

    public boolean isEqual(ICalleeObject eventHandle)
    {
        boolean ret = false;

        if(eventHandle != null)
        {
            ret = UtilSysLibsWrap.isAddressEqual(this.mEventHandle, eventHandle);
            if(!ret)
            {
                return ret;
            }
        }

        return ret;
    }

    public void call(TimerItemBase dispatchObject)
    {
        if (null != this.mEventHandle)
        {
            this.mEventHandle.call(dispatchObject);
        }
    }
}