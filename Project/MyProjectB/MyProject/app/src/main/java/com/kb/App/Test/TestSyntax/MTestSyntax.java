package com.kb.App.Test.TestSyntax;

import com.kb.App.Test.TestBase.MTestBase;
import com.kb.mylibs.Log.UtilLog;

public class MTestSyntax extends MTestBase
{
    public MTestSyntax()
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
        TestSyntaxBase_String test = new TestSyntaxBase_String();
        test.init();
        UtilLog.log("aaaaa");
    }
}