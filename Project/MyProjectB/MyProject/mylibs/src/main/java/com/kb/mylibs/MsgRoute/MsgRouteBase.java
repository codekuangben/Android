package com.kb.mylibs.MsgRoute;

import com.kb.mylibs.EventHandle.IDispatchObject;
import com.kb.mylibs.ObjectPool.IRecycle;

public class MsgRouteBase implements IRecycle, IDispatchObject
{
    public MsgRouteType mMsgType;
    public MsgRouteId mMsgID;          // 只需要一个 ID 就行了

    public MsgRouteBase(MsgRouteId id)
    {
        mMsgType = MsgRouteType.eMRT_BASIC;
        mMsgID = id;
    }

    public void resetDefault()
    {

    }
}