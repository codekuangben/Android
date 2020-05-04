package com.kb.mylibs.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kb.mylibs.EventHandle.IDispatchObject;

/**
 * @brief 对 SysLibs 的进一步 wrap
 */
public class UtilSysLibsWrap
{
    public static final String CR_LF = "\\r\\n";
    public static final String UNITY3D = "unity3d";

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

    public static int read(InputStream inputStream, byte[] byteArray, int offset, int length)
    {
        int ret = 0;
        try
        {
            if (null != inputStream && null != byteArray)
            {
                if (-1 == length)
                {
                    length = byteArray.length;
                }

                ret = inputStream.read(byteArray, offset, length);
            }
        }
        catch(IOException error)
        {

        }

        return ret;
    }
}