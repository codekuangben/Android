package com.kb.mylibs.Task;

import com.kb.mylibs.DataStruct.LockQueue;

public class TaskQueue extends LockQueue<ISimpleTask>
{
    public TaskThreadPool mTaskThreadPool;

    public TaskQueue(String name)
    {
        super(ISimpleTask.class, name);
    }

    @Override
    public void push(ISimpleTask item)
    {
        super.push(item);
        // 检查是否有线程空闲，如果有就唤醒
        this.mTaskThreadPool.notifyIdleThread();
    }
}