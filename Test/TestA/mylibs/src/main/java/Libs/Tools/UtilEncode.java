package Libs.Tools;

public class UtilEncode
{
    static public byte[] getBytes(String srcStr, MEncoding encode)
    {
        byte[] ret = null;

        ret = encode.GetBytes(srcStr);

        return ret;
    }
}
