package SDK.Lib.DataStruct;

import SDK.Lib.Thread.*;

/**
 * @brief 线程安全列表， T 是 Object ，便于使用 Equal 比较地址
 */
public class LockList<T>
{
    protected DynBuffer<T> mDynamicBuffer;
    protected MMutex mVisitMutex;
    protected T mRetItem;

    public LockList(String name)
    {
        this(name, 32, 8 * 1024 * 1024);
    }

    public LockList(String name, int initCapacity)
    {
        this(name, initCapacity, 8 * 1024 * 1024);
    }

    public LockList(String name, int initCapacity, int maxCapacity)
    {
        mDynamicBuffer = new DynBuffer<T>(initCapacity, maxCapacity);
        mVisitMutex = new MMutex(false, name);
    }

    public int getCount()
    {
        MLock mlock = new MLock(mVisitMutex);
        int ret = mDynamicBuffer.mSize;
        mlock.Dispose();
        return ret;
    }

    public T get(int index)
    {
        MLock mlock = new MLock(mVisitMutex);

        {
            if (index < mDynamicBuffer.mSize) {
                mlock.Dispose();

                return mDynamicBuffer.mBuffer[index];
            } else {
                mlock.Dispose();

                return null;
            }
        }
    }

    public void set(int index, T value)
    {
        MLock mlock = new MLock(mVisitMutex);

        {
            mDynamicBuffer.mBuffer[index] = value;
        }

        mlock.Dispose();
    }

    public void Add(T item)
    {
        MLock mlock = new MLock(mVisitMutex);

        {
            if (mDynamicBuffer.mSize >= mDynamicBuffer.mCapacity)
            {
                mDynamicBuffer.extendDeltaCapicity(1);
            }

            mDynamicBuffer.mBuffer[mDynamicBuffer.mSize] = item;
            ++mDynamicBuffer.mSize;
        }

        mlock.Dispose();
    }

    public boolean Remove(T item)
    {
        MLock mlock = new MLock(mVisitMutex);

        {
            int idx = 0;

            for(T elem : mDynamicBuffer.mBuffer)
            {
                if(item.equals(elem))       // 地址比较
                {
                    this.RemoveAt(idx);
                    mlock.Dispose();

                    return true;
                }

                ++idx;
            }

            mlock.Dispose();

            return false;
        }
    }

    public T RemoveAt(int index)
    {
        MLock mlock = new MLock(mVisitMutex);

        {
            if (index < mDynamicBuffer.mSize)
            {
                mRetItem = mDynamicBuffer.mBuffer[index];

                if (index < mDynamicBuffer.mSize)
                {
                    if (index != mDynamicBuffer.mSize - 1 && 1 != mDynamicBuffer.mSize) // 如果删除不是最后一个元素或者总共就大于一个元素
                    {
                        MArray.Copy(mDynamicBuffer.mBuffer, index + 1, mDynamicBuffer.mBuffer, index, mDynamicBuffer.mSize - 1 - index);
                    }

                    --mDynamicBuffer.mSize;
                }
            }
            else
            {
                mRetItem = null;
            }

            mlock.Dispose();

            return mRetItem;
        }
    }

    public int IndexOf(T item)
    {
        MLock mlock = new MLock(mVisitMutex);

        {
            int idx = 0;
            for(T elem : mDynamicBuffer.mBuffer)
            {
                if (item.equals(elem))       // 地址比较
                {
                    this.RemoveAt(idx);
                    mlock.Dispose();

                    return idx;
                }

                ++idx;
            }

            mlock.Dispose();

            return -1;
        }
    }
}