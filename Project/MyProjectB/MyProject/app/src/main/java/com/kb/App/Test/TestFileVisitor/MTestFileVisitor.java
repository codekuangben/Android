package com.kb.App.Test.TestFileVisitor;

import com.kb.App.Test.TestBase.MTestBase;
import com.kb.mylibs.FrameWork.Ctx;

public class MTestFileVisitor extends MTestBase
{
    public MTestFileVisitor()
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
        Ctx.mInstance.mAssetManager.open("Config.TestA.txt");
    }
}