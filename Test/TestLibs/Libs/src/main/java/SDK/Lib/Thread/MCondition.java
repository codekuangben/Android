package SDK.Lib.Thread;

/**
 * @brief 单一对象同步
 */
public class MCondition
{
    protected MMutex mMutex;
    protected MEvent mEvent;
    protected boolean mCanEnterWait;  // 是否可以进入等待

    public MCondition(String name)
    {
        this.mMutex = new MMutex(false, name);
        this.mEvent = new MEvent(false);
        this.mCanEnterWait = true;      // 允许进入等待状态
    }

    public boolean getCanEnterWait()
    {
        return this.mCanEnterWait;
    }

    public void waitImpl()
    {
        //using (MLock mlock = new MLock(mMutex))
        //{
            this.mMutex.WaitOne();
            if (this.mCanEnterWait)
            {
                this.mMutex.ReleaseMutex();   // 这个地方需要释放锁，否则 notifyAll 进不来
                this.mEvent.WaitOne();
                this.mEvent.Reset();      // 重置信号
            }
            else
            {
                this.mCanEnterWait = true;
                this.mMutex.ReleaseMutex();
            }
        //}
    }

    public void notifyAllImpl()
    {
        MLock mlock = new MLock(mMutex);

        {
            if (this.mCanEnterWait) // 如果 mCanEnterWait == false，必然不能进入等待
            {
                this.mCanEnterWait = false;
                this.mEvent.Set();        // 唤醒线程
            }
        }
    }
}