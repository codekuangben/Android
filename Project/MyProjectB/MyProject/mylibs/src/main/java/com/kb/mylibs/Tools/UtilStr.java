package com.kb.mylibs.Tools;

/**
 * @brief 对 api 的进一步 wrap
 */
public class UtilStr
{
    public static byte[] ConvStr2Byte(String strContent)
    {
        return strContent.getBytes();
    }

    // "UTF-8"
    public static String ConvByte2Str(byte[] byteArray)
    {
        String ret = new String(byteArray);
        return ret;
    }
}