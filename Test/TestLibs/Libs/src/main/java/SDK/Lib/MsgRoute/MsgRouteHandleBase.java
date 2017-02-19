package SDK.Lib.MsgRoute;

import SDK.Lib.Core.GObject;
import SDK.Lib.DataStruct.MDictionary;
import SDK.Lib.EventHandle.AddOnceEventDispatch;
import SDK.Lib.EventHandle.ICalleeObject;
import SDK.Lib.EventHandle.IDispatchObject;

public class MsgRouteHandleBase extends GObject, ICalleeObject
{
    public MDictionary<Integer, AddOnceEventDispatch> mId2HandleDic;

    public MsgRouteHandleBase()
    {
        this.mTypeId = "MsgRouteHandleBase";

        this.mId2HandleDic = new MDictionary<Integer, AddOnceEventDispatch>();
    }

    public void addMsgRouteHandle(MsgRouteID msgRouteID, IDispatchObject handle)
    {
        if(!this.mId2HandleDic.ContainsKey((int)msgRouteID))
        {
            this.mId2HandleDic.set((int)msgRouteID, new AddOnceEventDispatch());
        }

        this.mId2HandleDic.get((int)msgRouteID).addEventHandle(null, handle);
    }

    public void removeMsgRouteHandle(MsgRouteID msgRouteID, IDispatchObject handle)
    {
        if (this.mId2HandleDic.ContainsKey((int)msgRouteID))
        {
            this.mId2HandleDic.get((int)msgRouteID).removeEventHandle(null, handle);
        }
    }

    public void handleMsg(IDispatchObject dispObj)
    {
        MsgRouteBase msg = (MsgRouteBase)dispObj;

        if (this.mId2HandleDic.ContainsKey((int)msg.mMsgID))
        {
            this.mId2HandleDic.get((int)msg.mMsgID).dispatchEvent(msg);
        }
        else
        {

        }
    }

    public void call(IDispatchObject dispObj)
    {

    }
}