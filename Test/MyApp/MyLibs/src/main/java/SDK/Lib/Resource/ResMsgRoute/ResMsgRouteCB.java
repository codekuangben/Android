package SDK.Lib.Resource.ResMsgRoute;

import SDK.Lib.FrameWork.Ctx;
import SDK.Lib.MsgRoute.MsgRouteDispHandle;
import SDK.Lib.MsgRoute.MsgRouteType;
import SDK.Lib.Resource.Download.DownloadEventId;

public class ResMsgRouteCB extends MsgRouteDispHandle
{
    public ResMsgRouteCB()
    {
        this.init();
    }

    public void init()
    {
        this.addRouteHandle(MsgRouteType.eMRT_BASIC.ordinal(), Ctx.mInstance.mDownloadMgr, null, DownloadEventId.eEventId_HandleMsg);
    }
}