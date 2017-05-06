package SDK.Lib.EventHandle;

public class ResEventDispatch extends EventDispatch
{
    public ResEventDispatch()
    {

    }

    @Override
    public void dispatchEvent(IDispatchObject dispatchObject)
    {
        super.dispatchEvent(dispatchObject);

        this.clearEventHandle();
    }
}