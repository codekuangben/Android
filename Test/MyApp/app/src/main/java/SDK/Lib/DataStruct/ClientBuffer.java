package SDK.Lib.DataStruct;

import SDK.Lib.FrameWork.MacroDef;
import SDK.Lib.Thread.MLock;
import SDK.Lib.Thread.MMutex;
import SDK.Lib.Tools.EEndian;
import SDK.Lib.FrameWork.Ctx;

/**
 *@brief 网络数据缓冲区
 */
public class ClientBuffer
{
    protected MsgBuffer mRawBuffer;      // 直接从服务器接收到的原始的数据，可能压缩和加密过
    protected MsgBuffer mMsgBuffer;      // 可以使用的缓冲区
    //protected ByteBuffer mSendTmpBA;   // 发送临时缓冲区，发送的数据都暂时放在这里
    protected MsgBuffer mSendTmpBuffer;  // 发送临时缓冲区，发送的数据都暂时放在这里
    protected ByteBuffer mSocketSendBA;       // 真正发送缓冲区

    protected DynByteBuffer mDynBuffer;        // 接收到的临时数据，将要放到 mRawBuffer 中去
    protected ByteBuffer mUnCompressHeaderBA;  // 存放解压后的头的长度
    protected ByteBuffer mSendData;            // 存放将要发送的数据，将要放到 m_sendBuffer 中去
    protected ByteBuffer mTmpData;             // 临时需要转换的数据放在这里
    protected ByteBuffer mTmp1fData;           // 临时需要转换的数据放在这里

    private MMutex mReadMutex;   // 读互斥
    private MMutex mWriteMutex;  // 写互斥

    public ClientBuffer()
    {
        mRawBuffer = new MsgBuffer();
        mMsgBuffer = new MsgBuffer();
        //mSendTmpBA = new ByteBuffer();
        mSendTmpBuffer = new MsgBuffer();
        mSocketSendBA = new ByteBuffer();
        //mSocketSendBA.mId = 1000;

        //mDynBuffer = new DynamicBuffer<byte>(8096);
        mUnCompressHeaderBA = new ByteBuffer();
        mSendData = new ByteBuffer();
        mTmpData = new ByteBuffer(4);
        mTmp1fData = new ByteBuffer(4);

        mReadMutex = new MMutex(false, "ReadMutex");
        mWriteMutex = new MMutex(false, "WriteMutex");
    }

    public DynByteBuffer getDynBuffer()
    {
        return mDynBuffer;
    }

    public MsgBuffer getSendTmpBuffer()
    {
        return mSendTmpBuffer;
    }

    public ByteBuffer getSendBuffer()
    {
        return mSocketSendBA;
    }

    public ByteBuffer getSendData()
    {
        return mSendData;
    }

    // 设置 ClientBuffer 字节序
    public void setEndian(EEndian end)
    {
        mRawBuffer.setEndian(end);
        mMsgBuffer.setEndian(end);

        mSendTmpBuffer.setEndian(end);
        mSocketSendBA.setEndian(end);

        mUnCompressHeaderBA.setEndian(end);
        mSendData.setEndian(end);
        mTmpData.setEndian(end);
        mTmp1fData.setEndian(end);
    }

    public MsgBuffer getRawBuffer()
    {
        return mRawBuffer;
    }

    public void SetRevBufferSize(int size)
    {
        mDynBuffer = new DynByteBuffer(size);
    }

    public void moveDyn2Raw()
    {
        // 接收到一个socket数据，就被认为是一个数据包，这个地方可能会有问题，服务器是这么发送的，只能这么处理，自己写入包的长度
        mTmp1fData.clear();
        mTmp1fData.writeUnsignedInt32(mDynBuffer.getSize());      // 填充长度
        mRawBuffer.getCircularBuffer().pushBackBA(mTmp1fData);
        // 写入包的数据
        mRawBuffer.getCircularBuffer().pushBackArr(mDynBuffer.getBuffer(), 0, mDynBuffer.getSize());
    }

    public void moveDyn2Raw_KBE()
    {
        // 写入包的数据
        mRawBuffer.getCircularBuffer().pushBackArr(mDynBuffer.getBuffer(), 0, mDynBuffer.getSize());
    }

    // 自己的消息逻辑
    public void moveRaw2Msg()
    {
        while (mRawBuffer.popFront())  // 如果有数据
        {
            //UnCompressAndDecryptAllInOne();
            UnCompressAndDecryptEveryOne();
        }
    }

    // KBEngine 引擎消息流程
    public void moveRaw2Msg_KBE()
    {
        this.mRawBuffer.getCircularBuffer().linearize();
        this.mMsgBuffer.getCircularBuffer().pushBackCB(this.mRawBuffer.getCircularBuffer());
        this.mRawBuffer.getCircularBuffer().clear();
    }

    public void send(boolean bnet)
    {
        mTmpData.clear();
        mTmpData.writeUnsignedInt32(mSendData.getLength());      // 填充长度

        if (bnet)       // 从 socket 发送出去
        {
            MLock mlock = new MLock(mWriteMutex);
            {
                //mSendTmpBA.writeUnsignedInt(mSendData.length);                            // 写入头部长度
                //mSendTmpBA.writeBytes(mSendData.dynBuff.buff, 0, mSendData.length);      // 写入内容

                mSendTmpBuffer.getCircularBuffer().pushBackBA(mTmpData);
                mSendTmpBuffer.getCircularBuffer().pushBackBA(mSendData);
            }

            mlock.Dispose();
        }
        else        // 直接放入接收消息缓冲区
        {
            //mTmpData.clear();
            //mTmpData.writeUnsignedInt(mSendData.length);      // 填充长度

            mMsgBuffer.getCircularBuffer().pushBackBA(mTmpData);              // 保存消息大小字段
            mMsgBuffer.getCircularBuffer().pushBackBA(mSendData);             // 保存消息大小字段
        }
    }

    // TODO: KBEngine 引擎发送
    public void send_KBE(boolean isSendToNet)
    {
        mTmpData.clear();

        if (isSendToNet)       // 从 socket 发送出去
        {
            MLock mlock = new MLock(mWriteMutex);
            {
                mSendTmpBuffer.getCircularBuffer().pushBackBA(mSendData);
            }
            mlock.Dispose();
        }
        else        // 直接放入接收消息缓冲区
        {
            mMsgBuffer.getCircularBuffer().pushBackBA(mSendData);             // 保存消息大小字段
        }
    }

    public ByteBuffer getMsg()
    {
        MLock mlock = new MLock(mReadMutex);
        {
            if (mMsgBuffer.popFront())
            {
                return mMsgBuffer.getMsgBodyBA();
            }
        }
        mlock.Dispose();

        return null;
    }

    // 弹出 KBEngine 消息
    public ByteBuffer getMsg_KBE()
    {
        MLock mlock = new MLock(mReadMutex);
        {
            if (mMsgBuffer.popFrontAll())
            {
                return mMsgBuffer.getMsgBodyBA();
            }
        }
        mlock.Dispose();

        return null;
    }

    // 获取数据，然后压缩加密
    public void getSocketSendData()
    {
        mSocketSendBA.clear();

        // 获取完数据，就解锁
        MLock mlock = new MLock(mWriteMutex);
        {
            //mSocketSendBA.writeBytes(mSendTmpBA.dynBuff.buff, 0, (uint)mSendTmpBA.length);
            //mSendTmpBA.clear();
            // 一次全部取出来发送出去
            //mSocketSendBA.writeBytes(mSendTmpBuffer.circularBuffer.buff, 0, (uint)mSendTmpBuffer.circuleBuffer.size);
            //mSendTmpBuffer.circularBuffer.clear();
            // 一次仅仅获取一个消息发送出去，因为每一个消息的长度要填写加密补位后的长度
            if (mSendTmpBuffer.popFront())     // 弹出一个消息，如果只有一个消息，内部会重置变量
            {
                mSocketSendBA.writeBytes(mSendTmpBuffer.getHeaderBA().getDynBuffer().getBuffer(), 0, mSendTmpBuffer.getHeaderBA().getLength());       // 写入头
                mSocketSendBA.writeBytes(mSendTmpBuffer.getMsgBodyBA().getDynBuffer().getBuffer(), 0, mSendTmpBuffer.getMsgBodyBA().getLength());             // 写入消息体
            }
        }
        mlock.Dispose();

        if (MacroDef.MSG_COMPRESS || MacroDef.MSG_ENCRIPT)
        {
            mSocketSendBA.setPos(0);
            CompressAndEncryptEveryOne();
            // CompressAndEncryptAllInOne();
        }
        mSocketSendBA.setPosition(0);       // 设置指针 pos
    }

    // TODO: KBEngine 获取发送数据
    public void getSocketSendData_KBE()
    {
        mSocketSendBA.clear();

        // 获取完数据，就解锁
        MLock mlock = new MLock(mWriteMutex);
        {
            if (mSendTmpBuffer.popFrontAll())
            {
                mSocketSendBA.writeBytes(mSendTmpBuffer.getMsgBodyBA().getDynBuffer().getBuffer(), 0, mSendTmpBuffer.getMsgBodyBA().getLength());             // 写入消息体
            }
        }
        mlock.Dispose();

        mSocketSendBA.setPos(0);        // 设置指针 pos
    }

    // 压缩加密每一个包
    protected void CompressAndEncryptEveryOne()
    {
        int origMsgLen = 0;    // 原始的消息长度，后面判断头部是否添加压缩标志
        int compressMsgLen = 0;
        int cryptLen = 0;
        boolean bHeaderChange = false; // 消息内容最前面的四个字节中消息的长度是否需要最后修正

        while (mSocketSendBA.getBytesAvailable() > 0)
        {
            if (MacroDef.MSG_COMPRESS && !MacroDef.MSG_ENCRIPT)
            {
                bHeaderChange = false;
            }

            origMsgLen = mSocketSendBA.readUnsignedInt32(origMsgLen);    // 读取一个消息包头

            if (MacroDef.MSG_COMPRESS)
            {
                if (origMsgLen > MsgCV.PACKET_ZIP_MIN)
                {

                }
                else
                {
                    mSocketSendBA.incPosDelta((int)origMsgLen);
                    compressMsgLen = origMsgLen;
                }
            }
            // 只加密消息 body
            //#if MSG_ENCRIPT
            //                mSocketSendBA.position -= compressMsgLen;      // 移动加密指针位置
            //                cryptLen = mSocketSendBA.encrypt(m_cryptKeyArr[(int)m_cryptAlgorithm], compressMsgLen, m_cryptAlgorithm);
            //                if (compressMsgLen != cryptLen)
            //                {
            //                    bHeaderChange = true;
            //                }
            //                compressMsgLen = cryptLen;
            //#endif

            // 加密如果系统补齐字节，长度可能会变成 8 字节的证书倍，因此需要等加密完成后再写入长度
            if (MacroDef.MSG_COMPRESS && !MacroDef.MSG_ENCRIPT)
            {
                if (origMsgLen > MsgCV.PACKET_ZIP_MIN)    // 如果原始长度需要压缩
                {
                    bHeaderChange = true;
                    origMsgLen = compressMsgLen;                // 压缩后的长度
                    origMsgLen |= MsgCV.PACKET_ZIP;            // 添加
                }

                if (bHeaderChange)
                {
                    mSocketSendBA.decPosDelta((int)compressMsgLen + 4);        // 移动到头部位置
                    mSocketSendBA.writeUnsignedInt32(origMsgLen, false);     // 写入压缩或者加密后的消息长度
                    mSocketSendBA.incPosDelta((int)compressMsgLen);              // 移动到下一个位置
                }
            }

            // 整个消息压缩后，包括 4 个字节头的长度，然后整个加密
            if (MacroDef.MSG_ENCRIPT)
            {
                cryptLen = ((compressMsgLen + 4 + 7) / 8) * 8 - 4;      // 计算加密后，不包括 4 个头长度的 body 长度
                if (origMsgLen > MsgCV.PACKET_ZIP_MIN)    // 如果原始长度需要压缩
                {
                    origMsgLen = cryptLen;                // 压缩后的长度
                    origMsgLen |= MsgCV.PACKET_ZIP;            // 添加
                }
                else
                {
                    origMsgLen = cryptLen;                // 压缩后的长度
                }

                mSocketSendBA.decPosDelta((int)(compressMsgLen + 4));        // 移动到头部位置
                mSocketSendBA.writeUnsignedInt32(origMsgLen, false);     // 写入压缩或者加密后的消息长度

                mSocketSendBA.decPosDelta(4);      // 移动到头部
            }
        }

        // 整个消息压缩后，包括 4 个字节头的长度，然后整个加密
//#if MSG_ENCRIPT
        //mSocketSendBA.position = 0;      // 移动到头部
        //mSocketSendBA.encrypt(m_cryptKeyArr[(int)m_cryptAlgorithm], 0, m_cryptAlgorithm);
//#endif
    }

    // 压缩解密作为一个包
    protected void CompressAndEncryptAllInOne()
    {
        int origMsgLen = mSocketSendBA.getLength();       // 原始的消息长度，后面判断头部是否添加压缩标志
        int compressMsgLen = 0;

        if (origMsgLen > MsgCV.PACKET_ZIP_MIN && MacroDef.MSG_COMPRESS)
        {

        }
        else if (MacroDef.MSG_ENCRIPT)
        {
            compressMsgLen = origMsgLen;
            mSocketSendBA.incPosDelta((int)origMsgLen);
        }

        if (MacroDef.MSG_ENCRIPT)
        {
            mSocketSendBA.decPosDelta((int)compressMsgLen);
        }

        if (MacroDef.MSG_COMPRESS || MacroDef.MSG_ENCRIPT)             // 如果压缩或者加密，需要再次添加压缩或者加密后的头长度
        {
            if (origMsgLen > MsgCV.PACKET_ZIP_MIN)    // 如果原始长度需要压缩
            {
                origMsgLen = compressMsgLen;
                origMsgLen |= MsgCV.PACKET_ZIP;            // 添加
            }
            else
            {
                origMsgLen = compressMsgLen;
            }

            mSocketSendBA.setPosition(0);
            mSocketSendBA.insertUnsignedInt32(origMsgLen);            // 写入压缩或者加密后的消息长度
        }
    }

    // 消息格式
    // |------------- 加密的整个消息  -------------------------------------|
    // |----4 Header----|-压缩的 body----|----4 Header----|-压缩的 body----|
    // |                |                |                |                |
    protected void UnCompressAndDecryptEveryOne()
    {
        if (MacroDef.MSG_ENCRIPT)
        {

        }
//#if MSG_COMPRESS
        //mRawBuffer.headerBA.setPos(0); // 这个头目前没有用，是客户端自己添加的，服务器发送一个包，就认为是一个完整的包
        //mRawBuffer.msgBodyBA.setPos(0);
        //uint msglen = mRawBuffer.headerBA.readUnsignedInt();
        //if ((msglen & DataCV.PACKET_ZIP) > 0)
        //{
        //    mRawBuffer.msgBodyBA.uncompress();
        //}
//#endif

        mRawBuffer.getMsgBodyBA().setPos(0);

        int msglen = 0;
        while (mRawBuffer.getMsgBodyBA().getBytesAvailable() >= 4)
        {
            msglen = mRawBuffer.getMsgBodyBA().readUnsignedInt32(msglen);    // 读取一个消息包头
            if (msglen == 0)     // 如果是 0 ，就说明最后是由于加密补齐的数据
            {
                break;
            }

            if ((msglen & MsgCV.PACKET_ZIP) > 0 && MacroDef.MSG_COMPRESS)
            {
                msglen &= (~MsgCV.PACKET_ZIP);         // 去掉压缩标志位
            }
            else
            {
                mRawBuffer.getMsgBodyBA().incPosDelta(msglen);
            }

            mUnCompressHeaderBA.clear();
            mUnCompressHeaderBA.writeUnsignedInt32(msglen);        // 写入解压后的消息的长度，不要写入 msglen ，如果压缩，再加密，解密后，再解压后的长度才是真正的长度
            mUnCompressHeaderBA.setPosition(0);

            MLock mlock = new MLock(mReadMutex);
            {
                mMsgBuffer.getCircularBuffer().pushBackBA(mUnCompressHeaderBA);             // 保存消息大小字段
                mMsgBuffer.getCircularBuffer().pushBackArr(mRawBuffer.getMsgBodyBA().getDynBuffer().getBuffer(), mRawBuffer.getMsgBodyBA().getPos() - msglen, msglen);      // 保存消息大小字段
            }
            mlock.Dispose();

            //Ctx.mInstance.mNetCmdNotify.addOneRevMsg();

            // Test 读取消息头
            // ByteBuffer buff = getMsg();
            // stNullUserCmd cmd = new stNullUserCmd();
            // cmd.derialize(buff);
        }
    }

    protected void UnCompressAndDecryptAllInOne()
    {
        if (MacroDef.MSG_ENCRIPT)
        {

        }

        int msglen = 0;
        if (MacroDef.MSG_COMPRESS)
        {
            mRawBuffer.getHeaderBA().setPos(0);

            msglen = mRawBuffer.getHeaderBA().readUnsignedInt32(msglen);
            if ((msglen & MsgCV.PACKET_ZIP) > 0)
            {

            }
        }

        if (!MacroDef.MSG_COMPRESS && !MacroDef.MSG_ENCRIPT)
        {
            mUnCompressHeaderBA.clear();
            mUnCompressHeaderBA.writeUnsignedInt32(mRawBuffer.getMsgBodyBA().getLength());
            mUnCompressHeaderBA.setPosition(0);
        }

        MLock mlock = new MLock(mReadMutex);
        {
            if (!MacroDef.MSG_COMPRESS && !MacroDef.MSG_ENCRIPT)
            {
                mMsgBuffer.getCircularBuffer().pushBackBA(mUnCompressHeaderBA);             // 保存消息大小字段
            }
            mMsgBuffer.getCircularBuffer().pushBackBA(mRawBuffer.getMsgBodyBA());      // 保存消息大小字段
        }
        mlock.Dispose();
    }
}