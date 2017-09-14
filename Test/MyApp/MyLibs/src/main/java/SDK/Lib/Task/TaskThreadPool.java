package SDK.Lib.Task;

import SDK.Lib.DataStruct.MList;

public class TaskThreadPool
{
    protected MList<TaskThread> mList;

    public TaskThreadPool()
    {

    }

    public void initThreadPool(int numThread, TaskQueue taskQueue)
    {
        this.mList = new MList<TaskThread>(numThread);
        int idx = 0;

        for(idx = 0; idx < numThread; ++idx)
        {
            this.mList.Add(new TaskThread(String.format("TaskThread%d", idx), taskQueue));
            this.mList.get(idx).start();
        }
    }

    public void notifyIdleThread()
    {
        for(TaskThread item : this.mList.list())
        {
            if(item.notifySelf())       // 如果唤醒某个线程就退出，如果一个都没有唤醒，说明当前线程都比较忙，需要等待
            {
                break;
            }
        }
    }
}