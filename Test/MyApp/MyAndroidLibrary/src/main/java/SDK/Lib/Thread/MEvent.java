package SDK.Lib.Thread;

//import java.util.concurrent.CyclicBarrier;

/**
 * @同步使用的 Event
 */
public class MEvent
{
    //private CyclicBarrier mEvent;
    private Object mEvent;

    public MEvent(boolean initialState)
    {
        //this.mEvent = new CyclicBarrier(2);
        this.mEvent = new Object();
    }

    synchronized public void WaitOne()
    {
        /**
         * @error java.lang.IllegalMonitorStateException: object not locked by thread before wait()
         * @ref http://bbs.csdn.net/topics/350162289/
         * @detail # 调用obj的wait(), notify()方法前，必须获得obj锁，也就是必须写在synchronized(obj) {...} 代码段内。
         * @detail synchronized(this)，会获得this的对象锁，dThread.wait()会让该调用线程wait在dThread对象的等待池中，然后该线程会释放dThread锁，由于线程没有dThread对象锁，所以会报IllegalMonitorStateException。同样的道理适合notify(),notifyAll().
        所以可以这样写：
         */
        synchronized(this.mEvent)
        {
            try
            {
                this.mEvent.wait();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    synchronized public boolean Reset()
    {
        //this.mEvent.reset();
        return true;
    }

    synchronized public boolean Set()
    {
        /**
         * @ref http://blog.csdn.net/zimo2013/article/details/40181349
         * @detail Java_Object_wait()、notify()、notifyAll()
         */
        synchronized(this.mEvent)
        {
            //this.mEvent.notify();
            this.mEvent.notifyAll();
        }

        return true;
    }
}