package com.kb.App.Test.TestByteBuffer;


import com.kb.App.Test.TestBase.MTestBase;
import com.kb.mylibs.DataStruct.MByteBuffer;


/**
 * @brief TestByteBuffer
 */

public class MTestByteBuffer extends MTestBase
{
    public MTestByteBuffer()
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