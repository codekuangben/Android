package com.kb.mylibs.MsgRoute;

import com.kb.mylibs.EventHandle.EventDispatchGroup;
import com.kb.mylibs.EventHandle.IDispatchObject;
import com.kb.mylibs.Log.UtilLog;

public class MsgRouteDispHandle
{
    protected EventDispatchGroup mEventDispatchGroup;

    public MsgRouteDispHandle()
    {
        this.mEventDispatchGroup = new EventDispatchGroup();
    }

    public void addRouteHandle(
            int groupId,
            MsgRouteHandleBase eventListener,
            IDispatchObject eventHandle,
            int eventId)
    {
        this.mEventDispatchGroup.addEventHandle(
                groupId,
                eventListener,
                eventHandle,
                eventId
        );
    }

    public void removeRouteHandle(
            int groupId,
            MsgRouteHandleBase eventListener,
            IDispatchObject eventHandle,
            int eventId
    )
    {
        this.mEventDispatchGroup.removeEventHandle(
                groupId,
                eventListener,
                eventHandle,
                eventId
        );
    }

    public void handleMsg(MsgRouteBase msg)
    {
        String textStr = "";

        if(this.mEventDispatchGroup.hasEventHandle(msg.mMsgType.ordinal()))
        {
            this.mEventDispatchGroup.dispatchEvent(msg.mMsgType.ordinal(), msg);
        }
        else
        {
            UtilLog.log("handleMsg can not find group");
        }
    }
}