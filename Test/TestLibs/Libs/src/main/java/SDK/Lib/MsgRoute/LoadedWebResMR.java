﻿package SDK.Lib.MsgRoute;

public class LoadedWebResMR extends MsgRouteBase
{
    public ITask m_task;

    public LoadedWebResMR()
    {
        super(MsgRouteID.eMRIDLoadedWebRes);
    }

    @Override
    public void resetDefault()
    {
        m_task = null;
    }
}