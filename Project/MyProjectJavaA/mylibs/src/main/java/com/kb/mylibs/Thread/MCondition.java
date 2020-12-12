package com.kb.mylibs.Thread;

/**
 * @brief 单一对象同步
 */
public class MCondition
{
    protected MEvent mEvent;
    protected MAtomicBoolean mCanWaitting;

    public MCondition(String name)
    {
        this.mEvent = new MEvent(false);
        this.mCanWaitting = new MAtomicBoolean(true);
    }

    public void waitImpl()
    {
        if (this.mCanWaitting.get())
        {
            this.mEvent.WaitOne();
            this.mEvent.Reset();      // 重置信号
        }

        this.mCanWaitting.set(true);
    }

    public boolean notifyAllImpl()
    {
        boolean ret = false;

        if (this.mCanWaitting.compareAndSet(true, false))
        {
            ret = true;
            this.mEvent.Set();        // 唤醒线程
        }

        return ret;
    }
}