package Libs.MsgRoute;

import Libs.Core.GObject;
import Libs.DataStruct.MDictionary;
import Libs.EventHandle.AddOnceEventDispatch;
import Libs.EventHandle.ICalleeObject;
import Libs.EventHandle.IDispatchObject;
import Libs.Log.UtilLogger;

public class MsgRouteHandleBase extends GObject implements ICalleeObject
{
    public MDictionary<Integer, AddOnceEventDispatch> mId2HandleDic;

    public MsgRouteHandleBase()
    {
        this.mTypeId = "MsgRouteHandleBase";

        this.mId2HandleDic = new MDictionary<Integer, AddOnceEventDispatch>();
    }

    public void addMsgRouteHandle(
            MsgRouteId msgRouteId,
            ICalleeObject pThis,
            IDispatchObject handle,
            int eventId)
    {
        if(!this.mId2HandleDic.ContainsKey(msgRouteId.ordinal()))
        {
            this.mId2HandleDic.set(msgRouteId.ordinal(), new AddOnceEventDispatch());
        }

        this.mId2HandleDic.get(msgRouteId.ordinal()).addEventHandle(
                pThis,
                handle,
                eventId
        );
    }

    public void removeMsgRouteHandle(
            MsgRouteId msgRouteId,
            ICalleeObject pThis,
            IDispatchObject handle,
            int eventId
    )
    {
        if (this.mId2HandleDic.ContainsKey(msgRouteId.ordinal()))
        {
            this.mId2HandleDic.get(msgRouteId.ordinal()).removeEventHandle(
                    pThis,
                    handle,
                    eventId
            );
        }
    }

    public void handleMsg(IDispatchObject dispObj)
    {
        MsgRouteBase msg = (MsgRouteBase)dispObj;

        if (this.mId2HandleDic.ContainsKey(msg.mMsgID.ordinal()))
        {
            this.mId2HandleDic.get(msg.mMsgID.ordinal()).dispatchEvent(msg);
        }
        else
        {
            UtilLogger.log("handleMsg can not id");
        }
    }

    public void call(IDispatchObject dispObj)
    {

    }
}