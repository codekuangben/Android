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
    protected LockList<IRecycle> mPoolList;

    public PoolSys()
    {
        this.mPoolList = new LockList<IRecycle>("PoolSys_List");
        this.mPoolList.getDynamicBuffer().setClassType(IRecycle.class);
        this.mPoolList.getDynamicBuffer().createBuffer();
    }

    //public <T> T newObject(Class<T> classT)
    public <T> T newObject(Class classT)
    {
        T retObj = null;
        // 查找
        int idx = 0;
        for(idx = 0; idx < this.mPoolList.getCount(); ++idx)
        {
            if (classT == this.mPoolList.get(idx).getClass())
            {
                retObj = (T)this.mPoolList.get(idx);

                this.mPoolList.RemoveAt(idx);

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
                        //myMethodInfo.invoke(retObj);
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
        if (this.mPoolList.IndexOf(obj) == -1)
        {
            this.mPoolList.Add(obj);
        }
    }
}