package Libs.FrameWork;

import Libs.DataStruct.ByteBuffer;

/**
 * @brief 生成一些需要的数据结构
 */
public class FactoryBuild
{
    public ByteBuffer buildByteBuffer()
    {
        return new ByteBuffer();
    }
}