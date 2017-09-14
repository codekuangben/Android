package SDK.Lib.Functor;

import SDK.Lib.EventHandle.ICalleeObject;
import SDK.Lib.EventHandle.IDispatchObject;

public class CallFuncObjectFixParam extends CallFuncObjectBase
{
    protected IDispatchObject mHandle;
    protected IDispatchObject mParam;

    public CallFuncObjectFixParam()
    {
        this.mHandle = null;
        this.mParam = null;
    }

    @Override
    public void clear()
    {
        this.mThis = null;
    }

    @Override
    public boolean isValid()
    {
        if (null != this.mHandle)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void setPThisAndHandle(ICalleeObject pThis, IDispatchObject handle, IDispatchObject param, int eventId)
    {
        super.setPThisAndHandle(pThis, handle, param, eventId);

        this.mHandle = handle;
        this.mParam = param;
        this.mEventId = eventId;
    }

    @Override
    public void call()
    {
        if (null != this.mHandle)
        {
            //this.mHandle(this.mParam);
            this.mThis.call(this.mParam, this.mEventId);
        }
    }
}