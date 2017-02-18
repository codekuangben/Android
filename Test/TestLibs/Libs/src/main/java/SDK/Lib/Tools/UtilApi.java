package SDK.Lib.Tools;

import SDK.Lib.EventHandle.IDispatchObject;

/**
 * @brief 对 api 的进一步 wrap
 */
public class UtilApi
{
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
}