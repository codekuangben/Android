/**
* @brief 定时器管理器
*/
package SDK.Lib.FrameHandle;

import SDK.Lib.DataStruct.MList;
import SDK.Lib.DelayHandle.DelayHandleMgrBase;
import SDK.Lib.DelayHandle.IDelayHandleItem;
import SDK.Lib.Tools.UtilApi;

public class TimerMgr extends DelayHandleMgrBase
{
    protected MList<TimerItemBase> mTimerList;     // 当前所有的定时器列表

    public TimerMgr()
    {
        this.mTimerList = new MList<TimerItemBase>();
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
        if (!this.mTimerList.Contains((TimerItemBase)delayObject))
        {
            if (this.mLoopDepth.isInDepth())
            {
                super.addObject(delayObject, priority);
            }
            else
            {
                this.mTimerList.Add((TimerItemBase)delayObject);
            }
        }
    }

    @Override
    protected void removeObject(IDelayHandleItem delayObject)
    {
        // 检查当前是否在队列中
        if (this.mTimerList.Contains((TimerItemBase)delayObject))
        {
            ((TimerItemBase)delayObject).mDisposed = true;

            if (this.mLoopDepth.isInDepth())
            {
                super.removeObject(delayObject);
            }
            else
            {
                for(TimerItemBase item : this.mTimerList.list())
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

    public void addTimer(TimerItemBase delayObject)
    {
        this.addTimer(delayObject, 0);
    }

    // 从 Lua 中添加定时器，这种定时器尽量整个定时器周期只与 Lua 通信一次
    public void addTimer(TimerItemBase delayObject, float priority)
    {
        this.addObject(delayObject, priority);
    }

    public void removeTimer(TimerItemBase timer)
    {
        this.removeObject(timer);
    }

    public void Advance(float delta)
    {
        this.mLoopDepth.incDepth();

        for(TimerItemBase timerItem : this.mTimerList.list())
        {
            if (!timerItem.isClientDispose())
            {
                timerItem.OnTimer(delta);
            }

            if (timerItem.mDisposed)        // 如果已经结束
            {
                this.removeObject(timerItem);
            }
        }

        this.mLoopDepth.decDepth();
    }
}