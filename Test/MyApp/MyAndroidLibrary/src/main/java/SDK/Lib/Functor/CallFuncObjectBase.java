package SDK.Lib.Functor;

import SDK.Lib.EventHandle.ICalleeObject;
import SDK.Lib.EventHandle.IDispatchObject;

public class CallFuncObjectBase
{
    protected ICalleeObject mThis;
    public int mEventId;

    public CallFuncObjectBase()
    {
        this.mThis = null;
        this.mEventId = 0;
    }

    public void setPThisAndHandle(ICalleeObject pThis, IDispatchObject handle, IDispatchObject param)
    {
        this.mThis = pThis;
    }

    public void setThisAndHandleNoParam(ICalleeObject pThis, IDispatchObject handle)
    {

    }

    public void clear()
    {
        this.mThis = null;
    }

    public boolean isValid()
    {
        return false;
    }

    public void call()
    {

    }
}