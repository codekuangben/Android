package SDK.Lib.DelayHandle;

import SDK.Lib.DataStruct.NoOrPriorityList.INoOrPriorityObject;
import SDK.Lib.DataStruct.NoOrPriorityList.NoPriorityList.NoPriorityList;
import SDK.Lib.DataStruct.NoOrPriorityList.PriorityList.PriorityList;
import SDK.Lib.FrameHandle.ITickedObject;

/**
 * @brief 延迟优先级处理管理器
 */
public class DelayPriorityHandleMgr extends DelayNoOrPriorityHandleMgr
{
    public DelayPriorityHandleMgr()
    {
        this.mDeferredAddQueue = new NoPriorityList();
        this.mDeferredAddQueue.setIsSpeedUpFind(true);
        this.mDeferredDelQueue = new NoPriorityList();
        this.mDeferredDelQueue.setIsSpeedUpFind(true);

        this.mNoOrPriorityList = new PriorityList();
        this.mNoOrPriorityList.setIsSpeedUpFind(true);
        this.mNoOrPriorityList.setIsOpKeepSort(true);
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

    public void addPriorityObject(INoOrPriorityObject priorityObject, float priority)
    {
        this.addNoOrPriorityObject(priorityObject, priority);
    }

    public void removePriorityObject(ITickedObject tickObj)
    {
        this.removeNoOrPriorityObject(tickObj);
    }
}