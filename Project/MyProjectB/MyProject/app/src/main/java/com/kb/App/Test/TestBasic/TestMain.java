package com.kb.App.Test.TestBasic;

import com.kb.App.Test.TestByteBuffer.TestByteBuffer;
import com.kb.App.Test.TestDataStruct.TestDataStruct;
import com.kb.App.Test.TestSyntax.TestSyntax;

/**
 * @brief TestMain
 */

public class TestMain
{
    protected TestByteBuffer mTestByteBuffer;
    protected TestDataStruct mTestDataStruct;
    protected TestSyntax mTestSyntax;

    public TestMain()
    {
        this.mTestByteBuffer = new TestByteBuffer();
        this.mTestDataStruct = new TestDataStruct();
        this.mTestSyntax = new TestSyntax();
    }

    public void run()
    {
        this.mTestByteBuffer.run();
        this.mTestDataStruct.run();
        this.mTestSyntax.run();
    }
}