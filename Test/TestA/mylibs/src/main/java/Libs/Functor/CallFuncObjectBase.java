package Libs.Functor;

import Libs.EventHandle.ICalleeObject;
import Libs.EventHandle.IDispatchObject;

public class CallFuncObjectBase
{
    protected ICalleeObject mThis;
    public int mEventId;

    public CallFuncObjectBase()
    {
        this.mThis = null;
        this.mEventId = 0;
    }

    public void setPThisAndHandle(ICalleeObject pThis, IDispatchObject handle, IDispatchObject param, int eventId)
    {
        this.mThis = pThis;
        this.mEventId = eventId;
    }

    public void setThisAndHandleNoParam(ICalleeObject pThis, IDispatchObject handle, int eventId)
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