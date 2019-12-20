package SDK.Lib.MsgRoute;

import SDK.Lib.Task.ITask;

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