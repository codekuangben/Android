package com.kb.mylibs.MsgRoute;

import com.kb.mylibs.Task.ISimpleTask;

public class LoadedWebResMR extends MsgRouteBase
{
    public ISimpleTask mTask;

    public LoadedWebResMR()
    {
        super(MsgRouteId.eMRIDLoadedWebRes);
    }

    @Override
    public void resetDefault()
    {
        mTask = null;
    }
}