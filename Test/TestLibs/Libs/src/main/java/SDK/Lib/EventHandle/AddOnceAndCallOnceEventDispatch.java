package SDK.Lib.DelayHandle;

public class AddOnceAndCallOnceEventDispatch extends EventDispatch
{
    override public void addEventHandle(ICalleeObject pThis, MAction<IDispatchObject> handle, LuaTable luaTable = null, LuaFunction luaFunction = null)
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