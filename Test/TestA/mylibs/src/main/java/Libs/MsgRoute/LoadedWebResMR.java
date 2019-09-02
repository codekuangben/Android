package Libs.MsgRoute;

import Libs.Task.ITask;

public class LoadedWebResMR extends MsgRouteBase
{
    public ITask mTask;

    public LoadedWebResMR()
    {
        super(MsgRouteID.eMRIDLoadedWebRes);
    }

    @Override
    public void resetDefault()
    {
        mTask = null;
    }
}