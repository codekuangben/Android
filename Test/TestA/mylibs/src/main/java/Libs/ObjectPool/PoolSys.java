package Libs.ObjectPool;

import java.lang.reflect.Method;

import Libs.DataStruct.LockList;
import Libs.Tools.TClassOp;

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
                        /**
                         * @brief 警告: 最后一个参数使用了不准确的变量类型的 varargs 方法的非 varargs 调用;
                         * @url http://blog.csdn.net/devilnov/article/details/50963927
                         错误信息：
                         警告： 最后一个参数使用了不准确的变量类型的 varargs 方法的非 varargs 调用；
                         [javac] 对于 varargs 调用，应使用 java.lang.Object
                         [javac] 对于非 varargs 调用，应使用 java.lang.Object[]，这样也可以抑制此警告

                         程序是一样的，在jdk1.4下可以编译通过，但在1.5就不行。上网查了一下，解决办法：
                         Method method  =  cls.getMethod( " hashCode " ,  new  Class[ 0 ]);  //  编译通过
                         Method method  =  cls.getMethod( " hashCode " ,  null );  //  编译失败

                         allMethod[i].invoke(dbInstance,  new  Object[]{});  //  编译通过
                         allMethod[i].invoke(dbInstance,  null );  //  编译失败
                         */
                        //myMethodInfo.invoke(retObj, null);
                        myMethodInfo.invoke(retObj, new  Object[]{});
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