package SDK.Lib.DataStruct;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

//public class MDictionary<TKey, TValue> where TValue : IComparer<TValue>
//public class MDictionary<TKey, TValue> where TValue : class
public class MDictionary<TKey, TValue>
{
    // 注：Dictionary 此类已过时。新的实现应该实现 Map 接口，而不是扩展此类。
    protected Map<TKey, TValue> mData;

    public MDictionary()
    {
        mData = new HashMap<TKey, TValue>();
    }

    public Map<TKey, TValue> getData()
    {
        return this.mData;
    }

    public int getCount()
    {
        return this.mData.keySet().size();
    }

    public TValue get(TKey key)
    {
        return this.value(key);
    }

    public void set(TKey key, TValue value)
    {
        this.Add(key, value);
    }

    public TValue value(TKey key)
    {
        if (this.mData.containsKey(key))
        {
            return this.mData.get(key);
        }

        return null;
    }

    public TKey key(TValue value)
    {
        for (TKey key : this.mData.keySet())
        {
            if (this.mData.get(key).equals(value))
            //if (kv.Value == value)
            {
                return key;
            }
        }

        return null;
    }

    public Set<TKey> getKeys()
    {
        return this.mData.keySet();
    }

    public Collection<TValue> getValues()
    {
        return this.mData.values();
    }

    public int Count()
    {
        return this.mData.keySet().size();
    }

    public Set<Map.Entry<TKey,TValue>> GetEnumerator()
    {
        return this.mData.entrySet();
    }

    public void Add(TKey key, TValue value)
    {
        this.mData.put(key, value);
    }

    public void Remove(TKey key)
    {
        this.mData.remove(key);
    }

    public void Clear()
    {
        this.mData.clear();
    }

    public boolean TryGetValue(TKey key, TValue value)
    {
        value = this.mData.get(key);
        return true;
    }

    public boolean ContainsKey(TKey key)
    {
        return this.mData.containsKey(key);
    }

    public boolean ContainsValue(TValue value)
    {
        for (TKey key : this.mData.keySet())
        {
            if (this.mData.get(key).equals(value))
            //if (kv.Value == value)
            {
                return true;
            }
        }

        return false;
    }

    public TValue at(int idx)
    {
        int curidx = 0;
        TValue ret = null;

        for (TKey key : this.mData.keySet())
        {
            if(curidx == idx)
            {
                ret = this.mData.get(key);
                break;
            }
        }

        return ret;
    }
}