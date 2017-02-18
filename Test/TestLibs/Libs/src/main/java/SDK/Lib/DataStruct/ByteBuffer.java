package SDK.Lib.DataStruct;

import SDK.Lib.EventHandle.*;
import SDK.Lib.Tools.*;

/**
 *@brief ByteBuffer 功能
 */
public class ByteBuffer implements IDispatchObject
{
    // 读写临时缓存，这个如果是单线程其实可以共享的
    public byte[] mWriteFloatBytes = null;
    public byte[] mWriteDoubleBytes = null;

    public byte[] mReadFloatBytes = null;
    public byte[] mReadDoubleBytes = null;

    protected DynByteBuffer mDynBuffer;
    protected int mPos;          // 当前可以读取的位置索引
    protected EEndian mEndian;          // 大端小端

    protected byte[] mPadBytes;

    public ByteBuffer()
    {
        this(BufferCV.INIT_CAPACITY, BufferCV.MAX_CAPACITY, EEndian.eLITTLE_ENDIAN);
    }

    public ByteBuffer(int initCapacity)
    {
        this(initCapacity, BufferCV.MAX_CAPACITY, EEndian.eLITTLE_ENDIAN);
    }

    public ByteBuffer(int initCapacity, int maxCapacity)
    {
        this(initCapacity, maxCapacity, EEndian.eLITTLE_ENDIAN);
    }

    public ByteBuffer(int initCapacity, int maxCapacity, EEndian endian)
    {
        this.mEndian = endian;        // 缓冲区默认是小端的数据，因为服务器是 linux 的
        this.mDynBuffer = new DynByteBuffer(initCapacity, maxCapacity);

        this.mReadFloatBytes = new byte[Float.SIZE];
        this.mReadDoubleBytes = new byte[Double.SIZE];
    }

    public DynByteBuffer getDynBuffer()
    {
        return this.mDynBuffer;
    }

    public int getBytesAvailable()
    {
        return (this.mDynBuffer.getSize() - this.mPos);
    }

    public EEndian getEndian()
    {
        return this.mEndian;
    }

    public void setEndian(EEndian end)
    {
        this.mEndian = end;
    }

    public int getLength()
    {
        return this.mDynBuffer.getSize();
    }

    public void setLength(int value)
    {
        this.mDynBuffer.setSize(value);
    }

    public void setPos(int pos)
    {
        this.mPos = pos;
    }

    public int getPos()
    {
        return this.mPos;
    }

    public int getPosition()
    {
        return this.mPos;
    }

    public void setPosition(int value)
    {
        this.mPos = value;
    }

    public void clear ()
    {
        this.mPos = 0;
        this.mDynBuffer.setSize(0);
    }

    // 检查是否有足够的大小可以扩展
    protected boolean canWrite(int delta)
    {
        if(this.mDynBuffer.getSize() + delta > this.mDynBuffer.getCapacity())
        {
            return false;
        }

        return true;
    }

    // 读取检查
    protected boolean canRead(int delta)
    {
        if (this.mPos + delta > this.mDynBuffer.getSize())
        {
            return false;
        }

        return true;
    }

    protected void extendDeltaCapicity(int delta)
    {
        this.mDynBuffer.extendDeltaCapicity(delta);
    }

    protected void advPos(int num)
    {
        this.mPos += num;
    }

    protected void advPosAndLen(int num)
    {
        this.mPos += num;
        this.setLength(this.mPos);
    }

    public void incPosDelta(int delta)        // 添加 pos delta 数量
    {
        this.mPos += (int)delta;
    }

    public void decPosDelta(int delta)     // 减少 pos delta 数量
    {
        this.mPos -= (int)delta;
    }

    public ByteBuffer readInt8(char tmpByte)
    {
        if (canRead(Character.SIZE))
        {
            tmpByte = (char)mDynBuffer.getBuffer()[(int)mPos];
            advPos(Character.SIZE);
        }

        return this;
    }

    public ByteBuffer readUnsignedInt8(byte tmpByte)
    {
        if (canRead(Byte.SIZE))
        {
            tmpByte = mDynBuffer.getBuffer()[(int)mPos];
            advPos(Byte.SIZE);
        }

        return this;
    }

    public ByteBuffer readInt16(short tmpShort)
    {
        if (canRead(Short.SIZE))
        {
            tmpShort = MBitConverter.ToInt16(mDynBuffer.getBuffer(), (int)mPos, mEndian);

            advPos(Short.SIZE);
        }

        return this;
    }

    public ByteBuffer readUnsignedInt16(short tmpUshort)
    {
        if (canRead(Short.SIZE))
        {
            tmpUshort = MBitConverter.ToUInt16(mDynBuffer.getBuffer(), (int)mPos, mEndian);

            advPos(Short.SIZE);
        }

        return this;
    }

    public ByteBuffer readInt32(int tmpInt)
    {
        if (canRead(Integer.SIZE))
        {
            tmpInt = MBitConverter.ToInt32(mDynBuffer.getBuffer(), (int)mPos, mEndian);

            advPos(Integer.SIZE);
        }

        return this;
    }

    public ByteBuffer readUnsignedInt32(int tmpUint)
    {
        if (canRead(Integer.SIZE))
        {
            // 如果字节序和本地字节序不同，需要转换
            tmpUint = MBitConverter.ToUInt32(mDynBuffer.getBuffer(), (int)mPos, mEndian);

            advPos(Integer.SIZE);
        }

        return this;
    }

    public ByteBuffer readInt64(long tmpLong)
    {
        if (canRead(Long.SIZE))
        {
            tmpLong = MBitConverter.ToInt64(mDynBuffer.getBuffer(), (int)mPos, mEndian);

            advPos(Long.SIZE);
        }

        return this;
    }

    public ByteBuffer readUnsignedInt64(long tmpUlong)
    {
        if (canRead(Long.SIZE))
        {
            tmpUlong = MBitConverter.ToUInt64(mDynBuffer.getBuffer(), (int)mPos, mEndian);

            advPos(Long.SIZE);
        }

        return this;
    }

    public ByteBuffer readFloat(float tmpFloat)
    {
        if (canRead(Float.SIZE))
        {
            if (mEndian != SystemEndian.msLocalEndian)
            {
                MArray.Copy(mDynBuffer.getBuffer(), (int)mPos, mReadFloatBytes, 0, Float.SIZE);
                MArray.Reverse(mReadFloatBytes, 0, Float.SIZE);
                tmpFloat = MBitConverter.ToFloat(mReadFloatBytes, (int)mPos);
            }
            else
            {
                tmpFloat = MBitConverter.ToFloat(mDynBuffer.getBuffer(), (int)mPos);
            }

            advPos(Float.SIZE);
        }

        return this;
    }

    public ByteBuffer readDouble(double tmpDouble)
    {
        if (canRead(Double.SIZE))
        {
            if (mEndian != SystemEndian.msLocalEndian)
            {
                MArray.Copy(mDynBuffer.getBuffer(), (int)mPos, mReadDoubleBytes, 0, Double.SIZE);
                MArray.Reverse(mReadDoubleBytes, 0, Double.SIZE);
                tmpDouble = MBitConverter.ToDouble(mReadDoubleBytes, (int)mPos);
            }
            else
            {
                tmpDouble = MBitConverter.ToDouble(mDynBuffer.getBuffer(), (int)mPos);
            }

            advPos(Double.SIZE);
        }

        return this;
    }

    //public ByteBuffer readMultiByte(ref string tmpStr, uint len, Encoding charSet)
    public ByteBuffer readMultiByte(String tmpStr, int len, GkEncode gkCharSet)
    {
        MEncoding charSet = UtilApi.convGkEncode2EncodingEncoding(gkCharSet);

        // 如果是 unicode ，需要大小端判断
        if (canRead(len))
        {
            tmpStr = charSet.GetString(mDynBuffer.getBuffer(), (int)mPos, (int)len);
            advPos(len);
        }

        return this;
    }

    // 这个是字节读取，没有大小端的区别
    public ByteBuffer readBytes(byte[] tmpBytes, int len)
    {
        if (canRead(len))
        {
            MArray.Copy(mDynBuffer.getBuffer(), (int)mPos, tmpBytes, 0, (int)len);
            advPos(len);
        }

        return this;
    }

    // 如果要使用 writeInt8 ，直接使用 writeMultiByte 这个函数
    public void writeInt8(char value)
    {
        if (!canWrite(Byte.SIZE))
        {
            extendDeltaCapicity(Byte.SIZE);
        }
        mDynBuffer.getBuffer()[mPos] = (byte)value;
        advPosAndLen(Byte.SIZE);
    }

    public void writeUnsignedInt8(byte value)
    {
        if (!canWrite(Byte.SIZE))
        {
            extendDeltaCapicity(Byte.SIZE);
        }
        mDynBuffer.getBuffer()[mPos] = value;
        advPosAndLen(Byte.SIZE);
    }

    public void writeInt16 (short value)
    {
        if (!canWrite(Short.SIZE))
        {
            extendDeltaCapicity(Short.SIZE);
        }

        MBitConverter.GetBytes(value, mDynBuffer.getBuffer(), (int)mPos, mEndian);

        advPosAndLen(Short.SIZE);
    }

    public void writeUnsignedInt16(short value)
    {
        if (!canWrite(Short.SIZE))
        {
            extendDeltaCapicity(Short.SIZE);
        }

        MBitConverter.GetBytes(value, mDynBuffer.getBuffer(), (int)mPos, mEndian);

        advPosAndLen(Short.SIZE);
    }

    public void writeInt32(int value)
    {
        if (!canWrite(Integer.SIZE))
        {
            extendDeltaCapicity(Integer.SIZE);
        }

        MBitConverter.GetBytes(value, mDynBuffer.getBuffer(), (int)mPos, mEndian);

        advPosAndLen(Integer.SIZE);
    }

    public void writeUnsignedInt32 (int value)
    {
        this.writeUnsignedInt32(value, true);
    }

    public void writeUnsignedInt32 (int value, boolean bchangeLen)
    {
        if (!canWrite(Integer.SIZE))
        {
            extendDeltaCapicity(Integer.SIZE);
        }

        MBitConverter.GetBytes(value, mDynBuffer.getBuffer(), (int)mPos, mEndian);

        if (bchangeLen)
        {
            advPosAndLen(Integer.SIZE);
        }
        else
        {
            advPos(Integer.SIZE);
        }
    }

    public void writeInt64(long value)
    {
        if (!canWrite(Long.SIZE))
        {
            extendDeltaCapicity(Long.SIZE);
        }

        MBitConverter.GetBytes(value, mDynBuffer.getBuffer(), (int)mPos, mEndian);

        advPosAndLen(Long.SIZE);
    }

    public void writeUnsignedInt64(long value)
    {
        if (!canWrite(Long.SIZE))
        {
            extendDeltaCapicity(Long.SIZE);
        }

        MBitConverter.GetBytes(value, mDynBuffer.getBuffer(), (int)mPos, mEndian);

        advPosAndLen(Long.SIZE);
    }

    public void writeFloat(float value)
    {
        if (!canWrite(Float.SIZE))
        {
            extendDeltaCapicity(Float.SIZE);
        }

        mWriteFloatBytes = MBitConverter.GetBytes(value);
        if (mEndian != SystemEndian.msLocalEndian)
        {
            MArray.Reverse(mWriteFloatBytes);
        }
        MArray.Copy(mWriteFloatBytes, 0, mDynBuffer.getBuffer(), mPos, Float.SIZE);

        advPosAndLen(Float.SIZE);
    }

    public void writeDouble(double value)
    {
        if (!canWrite(Double.SIZE))
        {
            extendDeltaCapicity(Double.SIZE);
        }

        mWriteDoubleBytes = MBitConverter.GetBytes(value);
        if (mEndian != SystemEndian.msLocalEndian)
        {
            MArray.Reverse(mWriteDoubleBytes);
        }
        MArray.Copy(mWriteDoubleBytes, 0, mDynBuffer.getBuffer(), mPos, Double.SIZE);

        advPosAndLen(Double.SIZE);
    }

    public void writeBytes(byte[] value, int start, int len)
    {
        this.writeBytes(value, start, len, true);
    }

    // 写入字节， bchangeLen 是否改变长度
    public void writeBytes(byte[] value, int start, int len, boolean bchangeLen)
    {
        if (len > 0)            // 如果有长度才写入
        {
            if (!canWrite(len))
            {
                extendDeltaCapicity(len);
            }
            MArray.Copy(value, start, mDynBuffer.getBuffer(), mPos, len);
            if (bchangeLen)
            {
                advPosAndLen(len);
            }
            else
            {
                advPos(len);
            }
        }
    }

    // 写入字符串
    //public void writeMultiByte(string value, Encoding charSet, int len)
    public void writeMultiByte(String value, GkEncode gkCharSet, int len)
    {
        MEncoding charSet = UtilApi.convGkEncode2EncodingEncoding(gkCharSet);
        int num = 0;

        if (null != value)
        {
            //char[] charPtr = value.ToCharArray();
            num = charSet.GetByteCount(value);

            if (0 == len)
            {
                len = num;
            }

            if (!canWrite((int)len))
            {
                extendDeltaCapicity((int)len);
            }

            if (num < len)
            {
                MArray.Copy(charSet.GetBytes(value), 0, mDynBuffer.getBuffer(), mPos, num);
                // 后面补齐 0
                MArray.Clear(mDynBuffer.getBuffer(), (int)(mPos + num), len - num);
            }
            else
            {
                MArray.Copy(charSet.GetBytes(value), 0, mDynBuffer.getBuffer(), mPos, len);
            }
            advPosAndLen((int)len);
        }
        else
        {
            if (!canWrite((int)len))
            {
                extendDeltaCapicity((int)len);
            }

            advPosAndLen((int)len);
        }
    }

    protected void replace(byte[] srcBytes, int srcStartPos)
    {
        this.replace(srcBytes, srcStartPos, 0, 0, 0);
    }

    protected void replace(byte[] srcBytes, int srcStartPos, int srclen_)
    {
        this.replace(srcBytes, srcStartPos, srclen_, 0, 0);
    }

    protected void replace(byte[] srcBytes, int srcStartPos, int srclen_, int destStartPos)
    {
        this.replace(srcBytes, srcStartPos, srclen_, destStartPos, 0);
    }

    // 替换已经有的一段数据
    protected void replace(byte[] srcBytes, int srcStartPos, int srclen_, int destStartPos, int destlen_)
    {
        int lastLeft = this.getLength() - destStartPos - destlen_;        // 最后一段的长度
        this.setLength(destStartPos + srclen_ + lastLeft);      // 设置大小，保证足够大小空间

        this.setPosition(destStartPos + srclen_);
        if (lastLeft > 0)
        {
            writeBytes(mDynBuffer.getBuffer(), destStartPos + destlen_, lastLeft, false);          // 这个地方自己区域覆盖自己区域，可以保证自己不覆盖自己区域
        }

        this.setPosition(destStartPos);
        writeBytes(srcBytes, srcStartPos, srclen_, false);
    }

    public void insertUnsignedInt32(int value)
    {
        this.setLength(this.getLength() + Integer.SIZE);       // 扩大长度
        writeUnsignedInt32(value);     // 写入
    }

    public ByteBuffer readUnsignedLongByOffset(long tmpUlong, int offset)
    {
        this.setPosition(offset);
        readUnsignedInt64(tmpUlong);
        return this;
    }

    // 写入 EOF 结束符
    public void end()
    {
        mDynBuffer.getBuffer()[this.getLength()] = 0;
    }

    public ByteBuffer readBoolean(boolean tmpBool)
    {
        if (canRead(Byte.SIZE))
        {
            tmpBool = MBitConverter.ToBoolean(mDynBuffer.getBuffer(), (int)mPos);
            advPos(Byte.SIZE);
        }

        return this;
    }
}