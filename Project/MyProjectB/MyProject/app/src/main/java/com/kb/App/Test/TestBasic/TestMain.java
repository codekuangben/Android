package com.kb.App.Test.TestBasic;

import com.kb.App.Test.TestByteBuffer.TestByteBuffer;

/**
 * @brief TestMain
 */

public class TestMain
{
    protected TestByteBuffer mTestByteBuffer;

    public TestMain()
    {
        this.mTestByteBuffer = new TestByteBuffer();
    }

    public void run()
    {
        this.mTestByteBuffer.run();
    }
}