package SDK.Lib.EventHandle;

public class AddOnceAndCallOnceEventDispatch extends EventDispatch
{
    @Override
    public void addEventHandle(ICalleeObject pThis, IDispatchObject handle)
    {
        if (!isExistEventHandle(pThis, handle, luaTable, luaFunction))
        {
            base.addEventHandle(pThis, handle, luaTable, luaFunction);
        }
    }

    override public void dispatchEvent(IDispatchObject dispatchObject)
    {
        base.dispatchEvent(dispatchObject);
        clearEventHandle();
    }
}