package SDK.Lib.MsgRoute;

import SDK.Lib.Core.GObject;
import SDK.Lib.DataStruct.MDictionary;
import SDK.Lib.EventHandle.AddOnceEventDispatch;
import SDK.Lib.EventHandle.ICalleeObject;
import SDK.Lib.EventHandle.IDispatchObject;

public class MsgRouteHandleBase extends GObject implements ICalleeObject
{
    public MDictionary<Integer, AddOnceEventDispatch> mId2HandleDic;

    public MsgRouteHandleBase()
    {
        this.mTypeId = "MsgRouteHandleBase";

        this.mId2HandleDic = new MDictionary<Integer, AddOnceEventDispatch>();
    }

    public void addMsgRouteHandle(MsgRouteID msgRouteID, IDispatchObject handle)
    {
        if(!this.mId2HandleDic.ContainsKey(msgRouteID.ordinal()))
        {
            this.mId2HandleDic.set(msgRouteID.ordinal(), new AddOnceEventDispatch());
        }

        this.mId2HandleDic.get(msgRouteID.ordinal()).addEventHandle(null, handle);
    }

    public void removeMsgRouteHandle(MsgRouteID msgRouteID, IDispatchObject handle)
    {
        if (this.mId2HandleDic.ContainsKey(msgRouteID.ordinal()))
        {
            this.mId2HandleDic.get(msgRouteID.ordinal()).removeEventHandle(null, handle);
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

    public void call(IDispatchObject dispObj)
    {

    }
}