package SDK.Lib.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import SDK.Lib.EventHandle.IDispatchObject;

/**
 * @brief 对 api 的进一步 wrap
 */
public class UtilApi
{
    public static final String CR_LF = "\\r\\n";

    static public MEncoding convGkEncode2EncodingEncoding(GkEncode gkEncode)
    {
        MEncoding retEncode = MEncoding.UTF8;

        if (GkEncode.eUTF8 == gkEncode)
        {
            retEncode = MEncoding.UTF8;
        }
        else if (GkEncode.eGB2312 == gkEncode)
        {
            retEncode = MEncoding.UTF8;
        }
        else if (GkEncode.eUnicode == gkEncode)
        {
            retEncode = MEncoding.Unicode;
        }
        else if (GkEncode.eDefault == gkEncode)
        {
            retEncode = MEncoding.Default;
        }

        return retEncode;
    }

    // 判断两个 GameObject 地址是否相等
    public static boolean isAddressEqual(Object a, Object b)
    {
        return a.equals(b);
    }

    // 判断两个函数是否相等，不能使用 isAddressEqual 判断函数是否相等
    public static boolean isDelegateEqual(IDispatchObject a, IDispatchObject b)
    {
        return a == b;
    }

    static public int getScreenWidth()
    {
        return 1000;
    }

    static public int getScreenHeight()
    {
        return 1000;
    }

    static public String getFormatTime()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    static public void printCallStack()
    {
        Throwable ex = new Throwable();

        StackTraceElement[] stackElements = ex.getStackTrace();

        if(stackElements != null)
        {
            for(int i = 0; i < stackElements.length; i++)
            {
                System.out.println(stackElements[i].getClassName());
                System.out.println(stackElements[i].getFileName());
                System.out.println(stackElements[i].getLineNumber());
                System.out.println(stackElements[i].getMethodName());
                System.out.println("-----------------------------------");
            }
        }
    }
}