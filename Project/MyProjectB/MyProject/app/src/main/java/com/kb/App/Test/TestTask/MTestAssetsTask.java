package com.kb.App.Test.TestTask;

import com.kb.mylibs.Log.UtilLog;
import com.kb.mylibs.Task.ISimpleTask;

public class MTestAssetsTask implements ISimpleTask
{
    public MTestAssetsTask()
    {

    }

    @Override
    public void runTask()
    {
        UtilLog.log("runTask");
    }

    @Override
    public void handleResult()
    {
        UtilLog.log("handleResult");
    }
}