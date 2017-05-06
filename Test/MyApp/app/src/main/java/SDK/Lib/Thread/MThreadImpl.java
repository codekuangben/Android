package SDK.Lib.Thread;

/**
 *
 * @brief 真正的线程实现
 */

public class MThreadImpl extends Thread
{
    protected IMRunnable mRunnable;

    public MThreadImpl(IMRunnable value)
    {
        this.mRunnable = value;
    }

    @Override
    public void run()
    {
        if(null != this.mRunnable)
        {
            this.mRunnable.run();
        }
    }
}
