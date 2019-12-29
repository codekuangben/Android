package com.kb.App.Test.TestByteBuffer;


import com.kb.App.Test.TestBasic.TestBase;
import com.kb.mylibs.DataStruct.MByteBuffer;


/**
 * @brief TestByteBuffer
 */

public class TestByteBuffer extends TestBase
{
    public TestByteBuffer()
    {

    }

    @Override
    public void run()
    {
        super.run();
        this.testBasic();
    }

    protected void testBasic()
    {
        MByteBuffer byteBuffer = new MByteBuffer();
        byteBuffer.writeInt8('a');
    }
}