package SDK.Lib.FrameWork;

public class Singleton<T>
{
    protected static T msSingleton;

    public static T getSingletonPtr()
    {
        if (null == msSingleton)
        {
            msSingleton = new T();
            msSingleton.init();
        }

        return msSingleton;
    }

    public static void deleteSingletonPtr()
    {
        if (null != msSingleton)
        {
            msSingleton.dispose();
            msSingleton = null;
        }
    }
}