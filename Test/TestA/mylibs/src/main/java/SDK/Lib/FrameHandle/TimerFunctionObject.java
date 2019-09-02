package SDK.Lib.FrameHandle;

import SDK.Lib.EventHandle.ICalleeObject;
import SDK.Lib.Tools.UtilApi;

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
            ret = UtilApi.isAddressEqual(this.mHandle, handle);
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