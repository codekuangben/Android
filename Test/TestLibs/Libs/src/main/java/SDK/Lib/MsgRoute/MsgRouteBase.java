package SDK.Lib.MsgRoute;

import SDK.Lib.EventHandle.IDispatchObject;

public class MsgRouteBase implements IRecycle, IDispatchObject
{
    public MsgRouteType mMsgType;
    public MsgRouteID mMsgID;          // 只需要一个 ID 就行了

    public MsgRouteBase(MsgRouteID id)
    {
        mMsgType = MsgRouteType.eMRT_BASIC;
        mMsgID = id;
    }

    public void resetDefault()
    {

    }
}