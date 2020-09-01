package com.kb.mylibs.FrameWork;

import com.kb.mylibs.EventHandle.AddOnceEventDispatch;
import com.kb.mylibs.EventHandle.ICalleeObject;
import com.kb.mylibs.EventHandle.IDispatchObject;

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

    public void addMainChildChangedHandle(
            ICalleeObject eventListener,
            IDispatchObject eventHandle,
            int eventId
    )
    {
        this.mMainChildMassChangedDispatch.addEventHandle(
                eventListener,
                eventHandle,
                eventId
        );
    }

    public void init()
    {

    }

    public void dispose()
    {

    }
}