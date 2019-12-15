package Libs.MsgRoute;

import Libs.EventHandle.EventDispatchGroup;
import Libs.EventHandle.IDispatchObject;
import Libs.FrameWork.Ctx;

public class MsgRouteDispHandle
{
    protected EventDispatchGroup mEventDispatchGroup;

    public MsgRouteDispHandle()
    {
        this.mEventDispatchGroup = new EventDispatchGroup();
    }

    public void addRouteHandle(int evtId, MsgRouteHandleBase pThis, IDispatchObject handle)
    {
        this.mEventDispatchGroup.addEventHandle(evtId, pThis, handle);
    }

    public void removeRouteHandle(int evtId, MsgRouteHandleBase pThis, IDispatchObject handle)
    {
        this.mEventDispatchGroup.removeEventHandle(evtId, pThis, handle);
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

        }
    }
}