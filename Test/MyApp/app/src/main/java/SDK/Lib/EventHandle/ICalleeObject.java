package SDK.Lib.EventHandle;

/**
 * @brief 可被调用的函数对象
 */
public interface ICalleeObject
{
    public void call(IDispatchObject dispObj);
}