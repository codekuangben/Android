package SDK.Lib.ObjectPool;

import SDK.Lib.DataStruct.LockList;

/**
 * @brief 对象池
 */
public class PoolSys
{
    //protected List<object> mPoolList = new List<object>();
    protected LockList<IRecycle> mPoolList = new LockList<IRecycle>("PoolSys_List");

    public T newObject<T>()
    {
        T retObj = null;
        // 查找
        int idx = 0;
        for(idx = 0; idx < mPoolList.Count; ++idx)
        {
            if (typeof(T) == mPoolList[idx].GetType())
            {
                retObj = (T)mPoolList[idx];
                mPoolList.RemoveAt(idx);

                MethodInfo myMethodInfo = retObj.GetType().GetMethod("resetDefault");

                if (myMethodInfo != null)
                {
                    myMethodInfo.Invoke(retObj, null);
                }

                return retObj;
            }
        }

        retObj = new T();

        return retObj;
    }

    public void deleteObj(IRecycle obj)
    {
        if (mPoolList.IndexOf(obj) == -1)
        {
            mPoolList.Add(obj);
        }
    }
}