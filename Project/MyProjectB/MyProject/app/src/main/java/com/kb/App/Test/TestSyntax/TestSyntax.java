package com.kb.App.Test.TestSyntax;

import com.kb.App.Test.TestBasic.TestBase;
import com.kb.mylibs.Log.UtilLog;

public class TestSyntax extends TestBase
{
    public TestSyntax()
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