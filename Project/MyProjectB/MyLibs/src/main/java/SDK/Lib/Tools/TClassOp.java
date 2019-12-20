package SDK.Lib.Tools;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @brief 模板类型信息操作
 */
public class TClassOp
{
    /**
     * @brief 获取类型信息中父类信息
     */
    //static public <T> Class getTClass(Class<T> classT, int index)
    static public Class getTClass(Class classT, int index)
    {
        Type genType = classT.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType))
        {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0)
        {
            throw new RuntimeException("Index outof bounds");
        }

        if (!(params[index] instanceof Class))
        {
            return Object.class;
        }

        return (Class)params[index];
    }

    //static public <T> T createObject(Class<T> classT)
    static public <T> T createObject(Class classT)
    {
        Constructor constructor = null;

        try
        {
            constructor = classT.getConstructor(new Class[]{});
        }
        catch(Exception e)
        {

        }

        T ret = null;

        try
        {
            if(null != constructor)
            {
                ret = (T) constructor.newInstance();
            }
        }
        catch(Exception e)
        {

        }

        return ret;
    }

    //static public <T> T[] createArray(Class<T> type, int initCapacity)
    static public <T> T[] createArray(Class type, int initCapacity)
    {
        return (T[]) Array.newInstance(type, initCapacity);
    }
}