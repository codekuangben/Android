/**
* @brief 定时器管理器
*/
package SDK.Lib.FrameHandle;

import SDK.Lib.DataStruct.MList;
import SDK.Lib.DelayHandle.DelayHandleMgrBase;
import SDK.Lib.DelayHandle.IDelayHandleItem;
import SDK.Lib.Tools.UtilApi;

public class FrameTimerMgr extends DelayHandleMgrBase
{
    protected MList<FrameTimerItem> mTimerList;     // 当前所有的定时器列表

    public FrameTimerMgr()
    {
        this.mTimerList = new MList<FrameTimerItem>();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void dispose()
    {

    }

    @Override
    protected void addObject(IDelayHandleItem delayObject)
    {
        this.addObject(delayObject, 0);
    }

    @Override
    protected void addObject(IDelayHandleItem delayObject, float priority)
    {
        // 检查当前是否已经在队列中
        if (!this.mTimerList.Contains((FrameTimerItem)delayObject))
        {
            if (this.mLoopDepth.isInDepth())
            {
                super.addObject(delayObject, priority);
            }
            else
            {
                this.mTimerList.Add((FrameTimerItem)delayObject);
            }
        }
    }

    @Override
    protected void removeObject(IDelayHandleItem delayObject)
    {
        // 检查当前是否在队列中
        if (this.mTimerList.Contains((FrameTimerItem)delayObject))
        {
            ((FrameTimerItem)delayObject).mDisposed = true;

            if (this.mLoopDepth.isInDepth())
            {
                super.addObject(delayObject);
            }
            else
            {
                for(FrameTimerItem item : this.mTimerList.list())
                {
                    if (UtilApi.isAddressEqual(item, delayObject))
                    {
                        this.mTimerList.Remove(item);
                        break;
                    }
                }
            }
        }
    }

    public void addFrameTimer(FrameTimerItem timer)
    {
        this.addFrameTimer(timer, 0);
    }

    public void addFrameTimer(FrameTimerItem timer, float priority)
    {
        this.addObject(timer, priority);
    }

    public void removeFrameTimer(FrameTimerItem timer)
    {
        this.removeObject(timer);
    }

    public void Advance(float delta)
    {
        this.mLoopDepth.incDepth();

        for(FrameTimerItem timerItem : this.mTimerList.list())
        {
            if (!timerItem.isClientDispose())
            {
                timerItem.OnFrameTimer();
            }
            if (timerItem.mDisposed)
            {
                removeObject(timerItem);
            }
        }

        this.mLoopDepth.decDepth();
    }
}