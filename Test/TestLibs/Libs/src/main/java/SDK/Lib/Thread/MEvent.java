package SDK.Lib.Thread;

import java.util.concurrent.CyclicBarrier;

/**
 * @同步使用的 Event
 */
public class MEvent
{
    private CyclicBarrier mEvent;

    public MEvent(boolean initialState)
    {
        this.mEvent = new CyclicBarrier(2);
    }

    public void WaitOne()
    {
        try
        {
            this.mEvent.await();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean Reset()
    {
        this.mEvent.reset();
        return true;
    }

    public boolean Set()
    {
        //return this.mEvent.Set();
        return false;
    }
}