package SDK.Lib.DelayHandle;

import SDK.Lib.DataStruct.NoOrPriorityList.INoOrPriorityObject;
import SDK.Lib.DataStruct.NoOrPriorityList.NoPriorityList.NoPriorityList;
import SDK.Lib.FrameHandle.ITickedObject;

/**
 * @brief 延迟优先级处理管理器
 */
public class DelayNoPriorityHandleMgr extends DelayNoOrPriorityHandleMgr
{
    public DelayNoPriorityHandleMgr()
    {
        this.mDeferredAddQueue = new NoPriorityList();
        this.mDeferredAddQueue.setIsSpeedUpFind(true);
        this.mDeferredDelQueue = new NoPriorityList();
        this.mDeferredDelQueue.setIsSpeedUpFind(true);

        this.mNoOrPriorityList = new NoPriorityList();
        this.mNoOrPriorityList.setIsSpeedUpFind(true);
    }

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    public void addNoPriorityObject(INoOrPriorityObject priorityObject)
    {
        this.addNoOrPriorityObject(priorityObject, 0.0f);
    }

    public void removeNoPriorityObject(ITickedObject tickObj)
    {
        this.removeNoOrPriorityObject(tickObj);
    }
}