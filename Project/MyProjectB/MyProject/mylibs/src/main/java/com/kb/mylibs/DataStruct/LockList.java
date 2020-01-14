package com.kb.mylibs.DataStruct;

import com.kb.mylibs.Thread.*;

/**
 * @brief 线程安全列表， T 是 Object ，便于使用 Equal 比较地址
 */
public class LockList<T>
{
    protected DynBuffer<T> mDynamicBuffer;
    protected MMutex mVisitMutex;
    protected T mRetItem;

    public LockList(Class<T> classInfo, String name)
    {
        this(classInfo, name, 32, 8 * 1024 * 1024);
    }

    public LockList(Class<T> classInfo, String name, int initCapacity)
    {
        this(classInfo, name, initCapacity, 8 * 1024 * 1024);
    }

    public LockList(Class<T> classInfo, String name, int initCapacity, int maxCapacity)
    {
        this.mDynamicBuffer = new DynBuffer<T>(classInfo, initCapacity, maxCapacity);
        this.mVisitMutex = new MMutex(false, name);
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
            if (index < this.mDynamicBuffer.mSize) {
                mlock.Dispose();

                return this.mDynamicBuffer.mBuffer[index];
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
            this.mDynamicBuffer.mBuffer[index] = value;
        }

        mlock.Dispose();
    }

    public void Add(T item)
    {
        MLock mlock = new MLock(mVisitMutex);

        {
            if (this.mDynamicBuffer.mSize >= this.mDynamicBuffer.mCapacity)
            {
                this.mDynamicBuffer.extendDeltaCapicity(1);
            }

            this.mDynamicBuffer.mBuffer[this.mDynamicBuffer.mSize] = item;
            ++this.mDynamicBuffer.mSize;
        }

        mlock.Dispose();
    }

    public boolean Remove(T item)
    {
        MLock mlock = new MLock(this.mVisitMutex);

        {
            int idx = 0;

            for(T elem : this.mDynamicBuffer.mBuffer)
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
        MLock mlock = new MLock(this.mVisitMutex);

        {
            if (index < this.mDynamicBuffer.mSize)
            {
                this.mRetItem = this.mDynamicBuffer.mBuffer[index];

                if (index < this.mDynamicBuffer.mSize)
                {
                    if (index != this.mDynamicBuffer.mSize - 1 &&
                        1 != this.mDynamicBuffer.mSize) // 如果删除不是最后一个元素或者总共就大于一个元素
                    {
                        MArray.Copy(
                                this.mDynamicBuffer.mBuffer,
                                index + 1,
                                this.mDynamicBuffer.mBuffer,
                                index,
                                this.mDynamicBuffer.mSize - 1 - index
                        );
                    }

                    --this.mDynamicBuffer.mSize;
                }
            }
            else
            {
                this.mRetItem = null;
            }

            mlock.Dispose();

            return this.mRetItem;
        }
    }

    public int IndexOf(T item)
    {
        MLock mlock = new MLock(this.mVisitMutex);

        {
            int idx = 0;
            for(T elem : this.mDynamicBuffer.mBuffer)
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