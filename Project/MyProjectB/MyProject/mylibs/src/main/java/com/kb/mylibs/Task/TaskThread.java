package com.kb.mylibs.Task;

import com.kb.mylibs.Thread.MCondition;
import com.kb.mylibs.Thread.MThread;

/**
 * @brief 任务线程
 */
public class TaskThread extends MThread
{
    protected TaskQueue mTaskQueue;
    protected MCondition mCondition;
    protected ISimpleTask mCurTask;

    public TaskThread(String name, TaskQueue taskQueue)
    {
        super(null, null);

        this.mTaskQueue = taskQueue;
        this.mCondition = new MCondition(name);
    }

    /**
     *brief 线程回调函数
     */
    @Override
    public void run()
    {
        while (!this.mIsExitFlag)
        {
            this.mCurTask = this.mTaskQueue.pop();

            if(this.mCurTask != null)
            {
                this.mCurTask.runTask();
            }
            else
            {
                this.mCondition.waitImpl();
            }
        }
    }

    public boolean notifySelf()
    {
        if(this.mCondition.getCanEnterWait())
        {
            this.mCondition.notifyAll();
            return true;
        }

        return false;
    }
}