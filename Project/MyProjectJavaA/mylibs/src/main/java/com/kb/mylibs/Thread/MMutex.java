package com.kb.mylibs.Thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kb.mylibs.FrameWork.*;

/**
 * @brief 互斥
 * @url https://blog.csdn.net/fan2012huan/article/details/51781443
 * （1）synchronized首选
 * （2）ReentrantLock
 * （3）Atomic原子类，效率较高，可用于优化
 * （4）ThreadLocal，特殊情况上使用，spring有介绍。以后再补充。
 */
public class MMutex
{
    private Lock mCriticalSection; 	// 读互斥
    private String mName;	    // name

    public MMutex(boolean initiallyOwned, String name)
    {
        if (MacroDef.NET_MULTHREAD)
        {
            this.mCriticalSection = new ReentrantLock();
            this.mName = name;
        }
    }

    public void lock()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            this.mCriticalSection.lock();
        }
    }

    public void unlock()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            this.mCriticalSection.unlock();
        }
    }

    public void close()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            //this.mMutex.Close();
        }
    }
}