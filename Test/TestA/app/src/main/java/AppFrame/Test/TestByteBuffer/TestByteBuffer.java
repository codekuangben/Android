package AppFrame.Test.TestByteBuffer;


import AppFrame.Test.TestBasic.TestBase;
import Libs.DataStruct.MByteBuffer;


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
