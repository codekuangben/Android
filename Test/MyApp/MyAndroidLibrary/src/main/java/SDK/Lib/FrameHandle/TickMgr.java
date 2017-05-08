package SDK.Lib.FrameHandle;

import SDK.Lib.DelayHandle.IDelayHandleItem;

/**
 * @brief 心跳管理器
 */
public class TickMgr extends TickObjectPriorityMgr
{
    public TickMgr()
    {

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

    @Override
    public void Advance(float delta, TickMode tickMode)
    {
        super.Advance(delta, tickMode);
    }

    public void addTick(ITickedObject tickObj, float priority)
    {
        this.addObject((IDelayHandleItem)tickObj, priority);
    }

    public void removeTick(ITickedObject tickObj)
    {
        this.removeObject((IDelayHandleItem)tickObj);
    }
}