package com.kb.mylibs.FrameWork;

import com.kb.mylibs.DataStruct.MByteBuffer;

/**
 * @brief 生成一些需要的数据结构
 */
public class FactoryBuild
{
    public MByteBuffer buildByteBuffer()
    {
        return new MByteBuffer();
    }
}