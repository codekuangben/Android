package Libs.Tools;

/**
 * @brief 对 api 的进一步 wrap
 */
public class UtilStr
{
    public static boolean isNullOrEmpty(String srcStr)
    {
        boolean ret = false;

        ret = (srcStr == null || srcStr.length() == 0);

        return ret;
    }
}