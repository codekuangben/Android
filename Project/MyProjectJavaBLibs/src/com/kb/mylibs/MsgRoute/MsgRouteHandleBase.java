package com.kb.mylibs.MsgRoute;

import com.kb.mylibs.Core.GObject;
import com.kb.mylibs.DataStruct.MDictionary;
import com.kb.mylibs.EventHandle.AddOnceEventDispatch;
import com.kb.mylibs.EventHandle.IEventListener;
import com.kb.mylibs.EventHandle.IDispatchObject;
import com.kb.mylibs.Log.UtilLog;

public class MsgRouteHandleBase extends GObject implements IEventListener
{
    public MDictionary<Integer, AddOnceEventDispatch> mId2HandleDic;

    public MsgRouteHandleBase()
    {
        this.mTypeId = "MsgRouteHandleBase";

        this.mId2HandleDic = new MDictionary<Integer, AddOnceEventDispatch>();
    }

    public void addMsgRouteHandle(
            MsgRouteId msgRouteId,
            IEventListener eventListener,
            IDispatchObject eventHandle,
            int eventId)
    {
        if(!this.mId2HandleDic.ContainsKey(msgRouteId.ordinal()))
        {
            this.mId2HandleDic.set(msgRouteId.ordinal(), new AddOnceEventDispatch());
        }

        this.mId2HandleDic.get(msgRouteId.ordinal()).addEventHandle(
                eventListener,
                eventHandle,
                eventId
        );
    }

    public void removeMsgRouteHandle(
            MsgRouteId msgRouteId,
            IEventListener eventListener,
            IDispatchObject eventHandle,
            int eventId
    )
    {
        if (this.mId2HandleDic.ContainsKey(msgRouteId.ordinal()))
        {
            this.mId2HandleDic.get(msgRouteId.ordinal()).removeEventHandle(
                    eventListener,
                    eventHandle,
                    eventId
            );
        }
    }

    public void handleMsg(IDispatchObject dispatchObject)
    {
        MsgRouteBase msg = (MsgRouteBase)dispatchObject;

        if (this.mId2HandleDic.ContainsKey(msg.mMsgID.ordinal()))
        {
            this.mId2HandleDic.get(msg.mMsgID.ordinal()).dispatchEvent(msg);
        }
        else
        {
            UtilLog.log("handleMsg can not id");
        }
    }

    public void call(IDispatchObject dispatchObject)
    {

    }
}