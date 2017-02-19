package SDK.Lib.Log;

/**
 * @brief 文件日志
 */
public class WinLogDevice extends LogDeviceBase
{
    @Override
    public void logout(String message)
    {
        this.logout(message, LogColor.eLC_LOG);
    }

    @Override
    public void logout(String message, LogColor type)
    {
        if (type == LogColor.eLC_LOG)
        {
            //Debug.Log(message);
        }
        else if (type == LogColor.eLC_WARN)
        {
            //Debug.LogWarning(message);
        }
        else if (type == LogColor.eLC_ERROR)
        {
            //Debug.LogError(message);
        }
    }
}