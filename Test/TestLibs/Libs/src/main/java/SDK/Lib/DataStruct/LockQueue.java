package SDK.Lib.DataStruct;

public class LockQueue<T>
{
    protected LockList<T> mList;

    public LockQueue(String name)
    {
        mList = new LockList<T>("name");
    }

    public void push(T item)
    {
        mList.Add(item);
    }

    public T pop()
    {
        return mList.RemoveAt(0);
    }
}