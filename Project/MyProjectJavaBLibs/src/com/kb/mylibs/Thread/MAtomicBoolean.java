package com.kb.mylibs.Thread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @brief Java 原子性实现 CAS操作 AtomInteger类
 * @url https://www.jianshu.com/p/652794ad4706
 */
public class MAtomicBoolean
{
    protected AtomicBoolean mAtomicBoolean;

    public MAtomicBoolean(boolean initialValue)
    {
        mAtomicBoolean = new AtomicBoolean(initialValue);
    }

    public boolean get()
    {
        return mAtomicBoolean.get();
    }

    public boolean compareAndSet(boolean expect, boolean update)
    {
        return mAtomicBoolean.compareAndSet(expect, update);
    }

    public void set(boolean newValue)
    {
        mAtomicBoolean.set(newValue);
    }
}
