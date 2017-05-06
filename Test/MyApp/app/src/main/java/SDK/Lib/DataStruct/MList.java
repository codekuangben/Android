package SDK.Lib.DataStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief 对系统 List 的封装
 */
public class MList<T>
{
    //public delegate int CompareFunc(T left, T right);

    protected ArrayList<T> mList;
    protected int mUniqueId;       // 唯一 Id ，调试使用

    protected MDictionary<T, Integer> mDic;    // 为了加快查找速度，当前 Element 到索引映射
    protected boolean mIsSpeedUpFind;  // 是否加快查询

    public MList()
    {
        this.mList = new ArrayList<T>();
        this.mDic = new MDictionary<T, Integer>();
        this.mIsSpeedUpFind = true;
    }

    public MList(int capacity)
    {
        this.mList = new ArrayList<T>(capacity);
    }

    public T[] ToArray()
    {
        return (T[])this.mList.toArray();
    }

    public List<T> list()
    {
        return this.mList;
    }

    public int getUniqueId()
    {
        return this.mUniqueId;
    }

    public void setUniqueId(int value)
    {
        this.mUniqueId = value;
    }

    public List<T> getBuffer()
    {
        return this.mList;
    }

    public int getSize()
    {
        return this.mList.size();
    }

    public void Add(T item)
    {
        this.mList.add(item);

        if (this.mIsSpeedUpFind)
        {
            this.mDic.set(item, this.mList.size() - 1);
        }
    }

    // 主要是 Add 一个 float 类型的 Vector3
    public void Add(T item_1, T item_2, T item_3)
    {
        this.mList.add(item_1);
        this.mList.add(item_2);
        this.mList.add(item_3);

        if(this.mIsSpeedUpFind)
        {
            this.mDic.set(item_1, this.mList.size() - 3);
            this.mDic.set(item_2, this.mList.size() - 2);
            this.mDic.set(item_3, this.mList.size() - 1);
        }
    }

    // 主要是 Add 一个 float 类型的 UV
    public void Add(T item_1, T item_2)
    {
        this.mList.add(item_1);
        this.mList.add(item_2);

        if (this.mIsSpeedUpFind)
        {
            this.mDic.set(item_1, this.mList.size() - 2);
            this.mDic.set(item_2, this.mList.size() - 1);
        }
    }

    // 主要是 Add 一个 byte 类型的 Color32
    public void Add(T item_1, T item_2, T item_3, T item_4)
    {
        this.mList.add(item_1);
        this.mList.add(item_2);
        this.mList.add(item_3);
        this.mList.add(item_4);

        if (this.mIsSpeedUpFind)
        {
            this.mDic.set(item_1, this.mList.size() - 4);
            this.mDic.set(item_2, this.mList.size() - 3);
            this.mDic.set(item_3, this.mList.size() - 2);
            this.mDic.set(item_4, this.mList.size() - 1);
        }
    }

    public void push(T item)
    {
        this.mList.add(item);

        if (this.mIsSpeedUpFind)
        {
            this.mDic.set(item, this.mList.size() - 1);
        }
    }

    public boolean Remove(T item)
    {
        if (this.mIsSpeedUpFind)
        {
            return this.effectiveRemove(item);
        }
        else
        {
            return this.mList.remove(item);
        }
    }

    public T get(int index)
    {
        return this.mList.get(index);
    }

    public void set(int index, T value)
    {
        if (this.mIsSpeedUpFind)
        {
            this.mDic.set(value, index);
        }

        this.mList.add(index, value);
    }

    public void Clear()
    {
        this.mList.clear();

        if (this.mIsSpeedUpFind)
        {
            this.mDic.Clear();
        }
    }

    public int Count()
    {
        return this.mList.size();
    }

    public int length()
    {
        return this.mList.size();
    }

    public void setLength(int value)
    {
        this.mList.ensureCapacity(value);
    }

    public void RemoveAt(int index)
    {
        if (this.mIsSpeedUpFind)
        {
            this.effectiveRemove(this.mList.get(index));
        }
        else
        {
            this.mList.remove(index);
        }
    }

    public int IndexOf(T item)
    {
        if (this.mIsSpeedUpFind)
        {
            if (this.mDic.ContainsKey(item))
            {
                return this.mDic.get(item);
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return this.mList.indexOf(item);
        }
    }

    public void Insert(int index, T item)
    {
        if (index <= this.Count())
        {
            if (this.mIsSpeedUpFind)
            {
                this.mDic.set(item, index);
            }

            //this.mList.Insert(index, item);
            this.mList.add(index, item);

            if (this.mIsSpeedUpFind)
            {
                this.updateIndex(index + 1);
            }
        }
        else
        {

        }
    }

    public boolean Contains(T item)
    {
        if (this.mIsSpeedUpFind)
        {
            return this.mDic.ContainsKey(item);
        }
        else
        {
            return this.mList.contains(item);
        }
    }

//    public void Sort(System.Comparison<T> comparer)
//    {
//        this.mList.Sort(comparer);
//    }

    public void merge(MList<T> appendList)
    {
        if(appendList != null)
        {
            for(T item : appendList.list())
            {
                this.mList.add(item);

                if (this.mIsSpeedUpFind)
                {
                    this.mDic.set(item, this.mList.size() - 1);
                }
            }
        }
    }

    // 快速移除元素
    protected boolean effectiveRemove(T item)
    {
        boolean ret = false;

        if (this.mDic.ContainsKey(item))
        {
            ret = true;

            int idx = this.mDic.get(item);
            this.mDic.Remove(item);

            if (idx == this.mList.size() - 1)    // 如果是最后一个元素，直接移除
            {
                //this.mList.RemoveAt(idx);
                this.mList.remove(idx);
            }
            else
            {
                this.mList.set(idx, this.mList.get(this.mList.size() - 1));
                //this.mList.RemoveAt(this.mList.size() - 1);
                this.mList.remove(this.mList.size() - 1);
                this.mDic.set(this.mList.get(idx), idx);
            }
        }

        return ret;
    }

    protected void updateIndex(int idx)
    {
        int len = this.mList.size();

        while(idx < len)
        {
            this.mDic.set(this.mList.get(idx), idx);

            ++idx;
        }
    }
}