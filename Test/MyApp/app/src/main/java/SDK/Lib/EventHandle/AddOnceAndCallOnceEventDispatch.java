package SDK.Lib.EventHandle;

public class AddOnceAndCallOnceEventDispatch extends EventDispatch
{
    @Override
    public void addEventHandle(ICalleeObject pThis, IDispatchObject handle)
    {
        if (!this.isExistEventHandle(pThis, handle))
        {
            super.addEventHandle(pThis, handle);
        }
    }

    @Override
    public void dispatchEvent(IDispatchObject dispatchObject)
    {
        super.dispatchEvent(dispatchObject);

        this.clearEventHandle();
    }
}