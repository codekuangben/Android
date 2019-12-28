package Libs.MsgRoute;

import Libs.EventHandle.EventDispatchGroup;
import Libs.EventHandle.IDispatchObject;
import Libs.FrameWork.Ctx;
import Libs.Log.UtilLogger;

public class MsgRouteDispHandle
{
    protected EventDispatchGroup mEventDispatchGroup;

    public MsgRouteDispHandle()
    {
        this.mEventDispatchGroup = new EventDispatchGroup();
    }

    public void addRouteHandle(
            int groupId,
            MsgRouteHandleBase pThis,
            IDispatchObject handle,
            int eventId)
    {
        this.mEventDispatchGroup.addEventHandle(
                groupId,
                pThis,
                handle,
                eventId
        );
    }

    public void removeRouteHandle(
            int groupId,
            MsgRouteHandleBase pThis,
            IDispatchObject handle,
            int eventId
    )
    {
        this.mEventDispatchGroup.removeEventHandle(
                groupId,
                pThis,
                handle,
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
            UtilLogger.log("handleMsg can not find group");
        }
    }
}