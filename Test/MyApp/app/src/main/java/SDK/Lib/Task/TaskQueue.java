package SDK.Lib.Task;

import SDK.Lib.DataStruct.LockQueue;

public class TaskQueue extends LockQueue<ITask>
{
    public TaskThreadPool mTaskThreadPool;

    public TaskQueue(String name)
    {
        super(name);
    }

    @Override
    public void push(ITask item)
    {
        super.push(item);

        // 检查是否有线程空闲，如果有就唤醒
        mTaskThreadPool.notifyIdleThread();
    }
}