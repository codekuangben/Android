package SDK.Lib.EventHandle;

public class AddOnceAndCallOnceEventDispatch extends EventDispatch
{
    @Override
    public void addEventHandle(ICalleeObject pThis, IDispatchObject handle, int eventId)
    {
        if (!this.isExistEventHandle(pThis, handle, eventId))
        {
            super.addEventHandle(pThis, handle, eventId);
        }
    }

    @Override
    public void dispatchEvent(IDispatchObject dispatchObject)
    {
        super.dispatchEvent(dispatchObject);

        this.clearEventHandle();
    }
}