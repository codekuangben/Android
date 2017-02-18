package SDK.Lib.DataStruct;

import java.lang.reflect.Array;

/**
 * @brief 动态增长的缓冲区，不是环形的，从 0 开始增长的
 */
public class DynBuffer<T>
{
    public int mCapacity;         // 分配的内存空间大小，单位大小是字节
    public int mMaxCapacity;      // 最大允许分配的存储空间大小
    public int mSize;              // 存储在当前缓冲区中的数量
    public T[] mBuffer;            // 当前环形缓冲区

    public DynBuffer()      // mono 模板类中使用常亮报错， vs 可以
    {
        this(1 * 1024/*DataCV.INIT_CAPACITY*/, 8 * 1024 * 1024/*DataCV.MAX_CAPACITY*/);
    }

    public DynBuffer(int initCapacity)
    {
        this(initCapacity, 8 * 1024 * 1024/*DataCV.MAX_CAPACITY*/);
    }

    public DynBuffer(int initCapacity, int maxCapacity)      // mono 模板类中使用常亮报错， vs 可以
    {
        this.mMaxCapacity = maxCapacity;
        this.mCapacity = initCapacity;
        this.mSize = 0;
        //this.mBuffer = new T[mCapacity];
        this.mBuffer = (T[]) new Object[this.mCapacity];
        //this.mBuffer = create(T.class, this.mCapacity);
    }

    public T[] create(Class<T> type, int initCapacity)
    {
        return (T[]) Array.newInstance(type, initCapacity);
    }

    public T[] getBuffer()
    {
        return this.mBuffer;
    }

    public void setBuffer(T[] value)
    {
        this.mBuffer = value;
        this.mCapacity = (int)this.mBuffer.Length;
    }

    public int getMaxCapacity()
    {
        return this.mMaxCapacity;
    }

    public void setMaxCapacity(int value)
    {
        this.mMaxCapacity = value;
    }

    public int getCapacity()
    {
            return this.mCapacity;
    }

    public void setCapacity(int value)
    {
        if (value == this.mCapacity)
        {
            return;
        }
        if (value < this.getSize())       // 不能分配比当前已经占有的空间还小的空间
        {
            return;
        }
        T[] tmpbuff = new T[value];   // 分配新的空间
        Array.Copy(this.mBuffer, 0, tmpbuff, 0, this.mSize);  // 这个地方是 mSize 还是应该是 mCapacity，如果是 CircleBuffer 好像应该是 mCapacity，如果是 ByteBuffer ，好像应该是 mCapacity。但是 DynBuffer 只有 ByteBuffer 才会使用这个函数，因此使用 mSize 就行了，但是如果使用 mCapacity 也没有问题

        this.mBuffer = tmpbuff;
        this.mCapacity = value;
    }

    public int getSize()
    {
        return this.mSize;
    }

    public void setSize(int value)
    {
        if (value > this.getCapacity())
        {
            this.extendDeltaCapicity(value - this.getSize());
        }

        this.mSize = value;
    }

    public void extendDeltaCapicity(int delta)
    {
        this.setCapacity(DynBufResizePolicy.getCloseSize(this.getSize() + delta, this.getCapacity(), this.getMaxCapacity());
    }
}