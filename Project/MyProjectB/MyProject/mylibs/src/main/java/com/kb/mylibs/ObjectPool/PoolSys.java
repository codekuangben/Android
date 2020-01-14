package com.kb.mylibs.ObjectPool;

import java.lang.reflect.Method;

import com.kb.mylibs.Core.GObject;
import com.kb.mylibs.DataStruct.LockList;
import com.kb.mylibs.DataStruct.MList;
import com.kb.mylibs.Tools.TClassOp;

/**
 * @brief 对象池
 */
public class PoolSys
{
    protected MList<GObject> mPoolList;
    //protected LockList<IRecycle> mPoolList;

    public PoolSys()
    {
        //this.mPoolList = new LockList<IRecycle>(IRecycle.class, "PoolSys_List");
        this.mPoolList = new MList<GObject>();
    }

    //public <T> T newObject(Class<T> classT)
    public <T> T newObject(Class classT)
    {
        T retObj = null;
        // 查找
        int idx = 0;

        for(idx = 0; idx < this.mPoolList.getSize(); ++idx)
        {
            if (classT == this.mPoolList.get(idx).getClass())
            {
                retObj = (T)this.mPoolList.get(idx);
                this.mPoolList.RemoveAt(idx);

                IRecycle recycle = (IRecycle)retObj;

                if(null != recycle)
                {
                    recycle.resetDefault();
                }

                //Method myMethodInfo = null;

                //try
                //{
                //    myMethodInfo = retObj.getClass().getMethod("resetDefault");
                //}
                //catch(Exception e)
                //{
                //
                //}

                //if (myMethodInfo != null)
                //{
                //    try
                //    {
                //        myMethodInfo.invoke(retObj, null);
                //    }
                //    catch(Exception e)
                //    {
                //
                //    }
                //}

                return retObj;
            }
        }

        retObj = TClassOp.createObject(classT);

        return retObj;
    }

    public void deleteObj(IRecycle obj)
    {
        if (this.mPoolList.IndexOf((GObject) obj) == -1)
        {
            this.mPoolList.Add((GObject)obj);
        }
    }
}