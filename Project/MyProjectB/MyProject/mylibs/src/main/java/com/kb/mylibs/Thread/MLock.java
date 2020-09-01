package com.kb.mylibs.Thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kb.mylibs.FrameWork.*;

/**
 * @brief 锁操作
 */
public class MLock
{
    protected MMutex mMutex;

    public MLock(MMutex mutex)
    {
        if (MacroDef.NET_MULTHREAD)
        {
            this.mMutex = mutex;
            mMutex.lock();
        }
    }

    public void lock()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            mMutex.lock();
        }
    }

    public void unlock()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            mMutex.unlock();
        }
    }

    public void Dispose()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            mMutex.unlock();
        }
    }
}