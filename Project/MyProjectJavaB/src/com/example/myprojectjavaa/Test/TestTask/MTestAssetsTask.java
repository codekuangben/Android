package com.kb.App.Test.TestTask;

import com.kb.mylibs.FrameWork.Ctx;
import com.kb.mylibs.Log.UtilLog;
import com.kb.mylibs.Task.ISimpleTask;
import com.kb.mylibs.Tools.UtilSysLibsWrap;

import java.io.InputStream;

public class MTestAssetsTask implements ISimpleTask
{
    public MTestAssetsTask()
    {

    }

    @Override
    public void runTask()
    {
        UtilLog.log("runTask");

        InputStream inputStream = Ctx.msIns.mAssetManager.open("Config/TestA.txt");

        byte[] byteArray = new byte[1024];

        while (UtilSysLibsWrap.read(inputStream, byteArray, 0, byteArray.length) > 0)
        {
            String strContent = new String(byteArray);
            com.kb.mylibs.Tools.UtilLog.log(strContent);
        }
    }

    @Override
    public void handleResult()
    {
        UtilLog.log("handleResult");
    }
}