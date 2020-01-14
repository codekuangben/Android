package com.kb.mylibs.Thread;

//import java.util.concurrent.CyclicBarrier;
//import threading;
//import time;

/**
 * @同步使用的 Event
 */
public class MEvent
{
    //private CyclicBarrier mEvent;
    private Object mEvent;
    //private threading.Event mEvent;

    public MEvent(boolean initialState)
    {
        //this.mEvent = new CyclicBarrier(2);
        this.mEvent = new Object();
    }

    synchronized public void WaitOne()
    {
        try
        {
            this.mEvent.wait();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    synchronized public boolean Reset()
    {
        //this.mEvent.reset();
        return true;
    }

    synchronized public boolean Set()
    {
        this.mEvent.notify();
        return true;
    }
}