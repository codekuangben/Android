package Libs.Resource.ResMsgRoute;

import Libs.FrameWork.Ctx;
import Libs.MsgRoute.MsgRouteDispHandle;
import Libs.MsgRoute.MsgRouteType;
import Libs.Resource.Download.DownloadEventId;

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