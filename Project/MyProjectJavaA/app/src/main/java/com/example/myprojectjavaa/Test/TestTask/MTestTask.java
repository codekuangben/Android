package com.kb.App.Test.TestTask;

import com.kb.App.Test.TestBase.MTestBase;
import com.kb.mylibs.FrameWork.Ctx;
import com.kb.mylibs.Task.ISimpleTask;

public class MTestTask extends MTestBase
{
    public MTestTask()
    {

    }

    @Override
    public void run()
    {
        super.run();
        this._testA();
    }

    protected void _testA()
    {
        MTestAssetsTask testAssetsTask = new MTestAssetsTask();
        Ctx.msIns.mTaskQueue.push(testAssetsTask);
        //ISimpleTask task = Ctx.msIns.mTaskQueue.pop();
    }
}