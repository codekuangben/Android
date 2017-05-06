package SDK.Lib.ObjectPool;

import java.lang.reflect.Method;

import SDK.Lib.DataStruct.LockList;
import SDK.Lib.Tools.TClassOp;

/**
 * @brief 对象池
 */
public class PoolSys
{
    //protected List<object> mPoolList = new List<object>();
    protected LockList<IRecycle> mPoolList = new LockList<IRecycle>("PoolSys_List");

    //public <T> T newObject(Class<T> classT)
    public <T> T newObject(Class classT)
    {
        T retObj = null;
        // 查找
        int idx = 0;
        for(idx = 0; idx < mPoolList.getCount(); ++idx)
        {
            if (classT == mPoolList.get(idx).getClass())
            {
                retObj = (T)mPoolList.get(idx);
                mPoolList.RemoveAt(idx);

                Method myMethodInfo = null;
                try
                {
                    myMethodInfo = retObj.getClass().getMethod("resetDefault");
                }
                catch(Exception e)
                {

                }

                if (myMethodInfo != null)
                {
                    try
                    {
                        myMethodInfo.invoke(retObj, null);
                    }
                    catch(Exception e)
                    {

                    }
                }

                return retObj;
            }
        }

        retObj = TClassOp.createObject(classT);

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