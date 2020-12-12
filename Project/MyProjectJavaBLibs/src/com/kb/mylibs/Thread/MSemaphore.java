package com.kb.mylibs.Thread;

import java.util.concurrent.Semaphore;

import com.kb.mylibs.FrameWork.*;

/**
 * @brief 互斥
 */
public class MSemaphore
{
    private Semaphore mSemaphore; 	// 读互斥
    private String mName;	// name

    public MSemaphore(boolean initiallyOwned, String name)
    {
        if (MacroDef.NET_MULTHREAD)
        {
            this.mSemaphore = new Semaphore(2);
            this.mName = name;
        }
    }

    public void WaitOne()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            try
            {
                this.mSemaphore.acquire();
            }
            catch (InterruptedException e)
            {

            }
        }
    }

    public void ReleaseMutex()
    {
        if (MacroDef.NET_MULTHREAD)
        {
            this.mSemaphore.release();
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