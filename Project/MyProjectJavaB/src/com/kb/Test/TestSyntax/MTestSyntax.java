package com.kb.Test.TestSyntax;

import com.kb.Test.TestBase.MTestBase;
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

    protected void _testB(String... args)
    {
        for (int i = 0; i < args.length; i++) {
            UtilLog.log(args[i]);
        }
    }
}