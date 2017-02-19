package SDK.Lib.Task;

import SDK.Lib.Thread.MCondition;
import SDK.Lib.Thread.MThread;

/**
 * @brief 任务线程
 */
public class TaskThread extends MThread
{
    protected TaskQueue mTaskQueue;
    protected MCondition mCondition;
    protected ITask mCurTask;

    public TaskThread(String name, TaskQueue taskQueue)
    {
        super(null, null);

        mTaskQueue = taskQueue;
        mCondition = new MCondition(name);
    }

    /**
     *brief 线程回调函数
     */
    @Override
    public void run()
    {
        while (!mIsExitFlag)
        {
            mCurTask = mTaskQueue.pop();
            if(mCurTask != null)
            {
                mCurTask.runTask();
            }
            else
            {
                mCondition.waitImpl();
            }
        }
    }

    public boolean notifySelf()
    {
        if(mCondition.getCanEnterWait())
        {
            mCondition.notifyAll();
            return true;
        }

        return false;
    }
}