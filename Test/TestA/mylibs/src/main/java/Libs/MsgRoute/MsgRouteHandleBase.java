package Libs.MsgRoute;

import Libs.Core.GObject;
import Libs.DataStruct.MDictionary;
import Libs.EventHandle.AddOnceEventDispatch;
import Libs.EventHandle.ICalleeObject;
import Libs.EventHandle.IDispatchObject;

public class MsgRouteHandleBase extends GObject implements ICalleeObject
{
    public MDictionary<Integer, AddOnceEventDispatch> mId2HandleDic;

    public MsgRouteHandleBase()
    {
        this.mTypeId = "MsgRouteHandleBase";

        this.mId2HandleDic = new MDictionary<Integer, AddOnceEventDispatch>();
    }

    public void init()
    {

    }

    public void dispose()
    {

    }

    public void addMsgRouteHandle(MsgRouteID msgRouteID, ICalleeObject pThis, IDispatchObject handle, int eventId)
    {
        if(!this.mId2HandleDic.ContainsKey(msgRouteID.ordinal()))
        {
            this.mId2HandleDic.set(msgRouteID.ordinal(), new AddOnceEventDispatch());
        }

        this.mId2HandleDic.get(msgRouteID.ordinal()).addEventHandle(pThis, handle, eventId);
    }

    public void removeMsgRouteHandle(MsgRouteID msgRouteID, IDispatchObject handle, int eventId)
    {
        if (this.mId2HandleDic.ContainsKey(msgRouteID.ordinal()))
        {
            this.mId2HandleDic.get(msgRouteID.ordinal()).removeEventHandle(null, handle, eventId);
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

        }
    }

    public void call(IDispatchObject dispObj, int eventId)
    {

    }
}