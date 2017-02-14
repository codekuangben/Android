package SDK.Lib.EventHandle;

/**
 * @brief 可被调用的函数对象
 */
interface ICalleeObject
{
    public void call(IDispatchObject dispObj);
}