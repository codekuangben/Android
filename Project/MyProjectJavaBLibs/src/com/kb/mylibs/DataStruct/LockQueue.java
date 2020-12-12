package com.kb.mylibs.DataStruct;

public class LockQueue<T>
{
    protected LockList<T> mList;

    public LockQueue(Class<T> classInfo, String name)
    {
        this.mList = new LockList<T>(classInfo, "name");
    }

    public void push(T item)
    {
        this.mList.Add(item);
    }

    public T pop()
    {
        return this.mList.RemoveAt(0);
    }
}