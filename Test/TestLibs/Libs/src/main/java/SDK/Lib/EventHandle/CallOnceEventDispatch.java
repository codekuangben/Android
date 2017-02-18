package SDK.Lib.EventHandle;

/**
 * @brief 一次事件分发，分发一次就清理
 */
public class CallOnceEventDispatch extends EventDispatch
{
    public CallOnceEventDispatch()
    {

    }

    @Override
    public void dispatchEvent(IDispatchObject dispatchObject)
    {
        super.dispatchEvent(dispatchObject);

        this.clearEventHandle();
    }
}