package SDK.Lib.Log;

/**
 * @brief 日志设备
 */
public abstract class LogDeviceBase
{
    public void initDevice()
    {

    }

    public void closeDevice()
    {

    }

    abstract public void logout(String message);

    abstract public void logout(String message, LogColor type);
}