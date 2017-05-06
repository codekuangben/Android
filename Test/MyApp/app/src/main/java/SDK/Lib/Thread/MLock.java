package SDK.Lib.Thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import SDK.Lib.FrameWork.*;

/**
 * @brief 锁操作
 */
public class MLock
{
    protected Lock mMutex;

    public MLock(MMutex mutex)
    {
        if (MacroDef.NET_MULTHREAD)
        {
            this.mMutex = new ReentrantLock();
            this.mMutex.lock();
        }
    }

    // 这个在超出作用域的时候就会被调用，但是只有在使用 using 语句中，例如 using (MLock mlock = new MLock(mReadMutex)) ，这个语句执行完后立马调用，using (MLock mlock = new MLock(mReadMutex)) {} 才行
    public void Dispose()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            mMutex.unlock();
        }
    }
}