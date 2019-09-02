package Libs.Functor;

import Libs.EventHandle.ICalleeObject;
import Libs.EventHandle.IDispatchObject;

public class CallFuncObjectNoParam extends CallFuncObjectBase
{
    protected IDispatchObject mHandleNoParam;

    public CallFuncObjectNoParam()
    {

    }

    @Override
    public void setThisAndHandleNoParam(ICalleeObject pThis, IDispatchObject handle, int eventId)
    {
        this.mThis = pThis;
        this.mHandleNoParam = handle;
    }

    @Override
    public void clear()
    {
        this.mHandleNoParam = null;

        super.clear();
    }

    @Override
    public boolean isValid()
    {
        if (null != this.mHandleNoParam)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void call()
    {
        if (null != this.mHandleNoParam)
        {
            //this.mHandleNoParam();
            this.mThis.call(null, this.mEventId);
        }
    }
}