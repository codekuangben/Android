package Libs.DelayHandle;

import Libs.DataStruct.NoOrPriorityList.NoPriorityList.NoPriorityList;

/**
 * @brief 当需要管理的对象可能在遍历中间添加的时候，需要这个管理器
 */
public class DelayNoPriorityHandleMgrBase extends DelayNoOrPriorityHandleMgrBase
{
    public DelayNoPriorityHandleMgrBase()
    {
        this.mDeferredAddQueue = new NoPriorityList();
        this.mDeferredAddQueue.setIsSpeedUpFind(true);
        this.mDeferredDelQueue = new NoPriorityList();
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