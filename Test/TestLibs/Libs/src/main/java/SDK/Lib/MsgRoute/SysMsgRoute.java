package SDK.Lib.MsgRoute;

import SDK.Lib.DataStruct.LockQueue;

/**
 * @brief 系统消息流程，整个系统的消息分发都走这里，仅限单线程
 */
public class SysMsgRoute extends LockQueue<MsgRouteBase>
{
    public SysMsgRoute(String name)
    {
        super(name);
    }

    public void init()
    {

    }

    public void dispose()
    {

    }
}