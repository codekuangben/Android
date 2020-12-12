package com.kb.App.Test.TestBase;

import com.kb.App.Test.TestByteBuffer.MTestByteBuffer;
import com.kb.App.Test.TestDataStruct.MTestDataStruct;
import com.kb.App.Test.TestSyntax.MTestSyntax;
import com.kb.App.Test.TestFileVisitor.MTestFileVisitor;
import com.kb.App.Test.TestTask.MTestTask;

/**
 * @brief TestMain
 */

public class TestMain
{
    protected MTestByteBuffer mTestByteBuffer;
    protected MTestDataStruct mTestDataStruct;
    protected MTestSyntax mTestSyntax;
    protected MTestFileVisitor mTestFileVisitor;
    protected MTestTask mTestTask;

    public TestMain()
    {
        this.mTestByteBuffer = new MTestByteBuffer();
        this.mTestDataStruct = new MTestDataStruct();
        this.mTestSyntax = new MTestSyntax();
        this.mTestFileVisitor = new MTestFileVisitor();
        this.mTestTask = new MTestTask();
    }

    public void init()
    {
        this.mTestByteBuffer.init();
        this.mTestDataStruct.init();
        this.mTestSyntax.init();
        this.mTestFileVisitor.init();
        this.mTestTask.init();
    }

    public void dispose()
    {
        this.mTestByteBuffer.dispose();
        this.mTestByteBuffer = null;
        this.mTestDataStruct.dispose();
        this.mTestDataStruct = null;
        this.mTestSyntax.dispose();
        this.mTestSyntax = null;
        this.mTestFileVisitor.dispose();
        this.mTestFileVisitor = null;
        this.mTestTask.dispose();
        this.mTestTask = null;
    }

    public void run()
    {
        this.mTestByteBuffer.run();
        this.mTestDataStruct.run();
        this.mTestSyntax.run();
        this.mTestFileVisitor.run();
        this.mTestTask.run();
    }
}