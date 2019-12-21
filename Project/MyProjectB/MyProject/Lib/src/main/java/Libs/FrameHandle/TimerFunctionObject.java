package Libs.FrameHandle;

import Libs.EventHandle.ICalleeObject;
import Libs.Tools.UtilSysLibsWrap;

public class TimerFunctionObject
{
    public ICalleeObjectTimer mHandle;

    public TimerFunctionObject()
    {
        this.mHandle = null;
    }

    public void setFuncObject(ICalleeObjectTimer handle)
    {
        this.mHandle = handle;
    }

    public boolean isValid()
    {
        return this.mHandle != null;
    }

    public boolean isEqual(ICalleeObject handle)
    {
        boolean ret = false;

        if(handle != null)
        {
            ret = UtilSysLibsWrap.isAddressEqual(this.mHandle, handle);
            if(!ret)
            {
                return ret;
            }
        }

        return ret;
    }

    public void call(TimerItemBase dispObj)
    {
        if (null != this.mHandle)
        {
            this.mHandle.call(dispObj);
        }
    }
}