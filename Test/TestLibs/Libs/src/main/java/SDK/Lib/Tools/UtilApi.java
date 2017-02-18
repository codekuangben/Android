package SDK.Lib.Tools;

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
}