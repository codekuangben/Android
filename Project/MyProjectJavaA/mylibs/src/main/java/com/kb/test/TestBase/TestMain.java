package com.kb.App.Test.TestBase;

import com.kb.App.Test.TestByteBuffer.MTestByteBuffer;
import com.kb.App.Test.TestDataStruct.MTestDataStruct;
import com.kb.App.Test.TestSyntax.MTestSyntax;

/**
 * @brief TestMain
 */

public class TestMain
{
    protected MTestByteBuffer mTestByteBuffer;
    protected MTestDataStruct mTestDataStruct;
    protected MTestSyntax mTestSyntax;

    public TestMain()
    {
        this.mTestByteBuffer = new MTestByteBuffer();
        this.mTestDataStruct = new MTestDataStruct();
        this.mTestSyntax = new MTestSyntax();
    }

    public void init()
    {
        this.mTestByteBuffer.init();
        this.mTestDataStruct.init();
        this.mTestSyntax.init();
    }

    public void dispose()
    {
        this.mTestByteBuffer.dispose();
        this.mTestByteBuffer = null;
        this.mTestDataStruct.dispose();
        this.mTestDataStruct = null;
        this.mTestSyntax.dispose();
        this.mTestSyntax = null;
    }

    public void run()
    {
        this.mTestByteBuffer.run();
        this.mTestDataStruct.run();
        this.mTestSyntax.run();
    }
}