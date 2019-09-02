package Libs.DelayHandle;

import Libs.DataStruct.NoOrPriorityList.PriorityList.PriorityList;

/**
 * @brief 当需要管理的对象可能在遍历中间添加的时候，需要这个管理器
 */
public class DelayPriorityHandleMgrBase extends DelayNoOrPriorityHandleMgrBase
{
    public DelayPriorityHandleMgrBase()
    {
        this.mDeferredAddQueue = new PriorityList();
        this.mDeferredAddQueue.setIsSpeedUpFind(true);
        this.mDeferredDelQueue = new PriorityList();
        this.mDeferredDelQueue.setIsSpeedUpFind(true);
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
}