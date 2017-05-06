package SDK.Lib.FrameWork;

import SDK.Lib.EventHandle.AddOnceEventDispatch;
import SDK.Lib.EventHandle.ICalleeObject;
import SDK.Lib.EventHandle.IDispatchObject;

/**
 * @brief 全局委托，只要初始化后，就可以注册和使用这些委托，不用等到哪一个资源创建完成
 */
public class GlobalDelegate
{
    // PlayerMainChild 的质量发生改变
    public AddOnceEventDispatch mMainChildMassChangedDispatch;

    public GlobalDelegate()
    {
        this.mMainChildMassChangedDispatch = new AddOnceEventDispatch();
    }

    public void addMainChildChangedHandle(ICalleeObject pThis, IDispatchObject handle)
    {
        this.mMainChildMassChangedDispatch.addEventHandle(pThis, handle);
    }

    public void init()
    {

    }

    public void dispose()
    {

    }
}