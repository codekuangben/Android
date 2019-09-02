package Libs.DataStruct;

import Libs.EventHandle.*;
import Libs.Tools.*;

/**
 *@brief MByteBuffer 功能
 */
public class MByteBuffer implements IDispatchObject
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

    public MByteBuffer()
    {
        this(BufferCV.INIT_CAPACITY, BufferCV.MAX_CAPACITY, EEndian.eLITTLE_ENDIAN);
    }

    public MByteBuffer(int initCapacity)
    {
        this(initCapacity, BufferCV.MAX_CAPACITY, EEndian.eLITTLE_ENDIAN);
    }

    public MByteBuffer(int initCapacity, int maxCapacity)
    {
        this(initCapacity, maxCapacity, EEndian.eLITTLE_ENDIAN);
    }

    public MByteBuffer(int initCapacity, int maxCapacity, EEndian endian)
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

    public MByteBuffer readInt8(char tmpByte)
    {
        if (this.canRead(Character.SIZE))
        {
            tmpByte = (char)this.mDynBuffer.getBuffer()[(int)this.mPos];

            this.advPos(Character.SIZE);
        }

        return this;
    }

    public byte readUnsignedInt8(byte tmpByte)
    {
        if (this.canRead(Byte.SIZE))
        {
            tmpByte = this.mDynBuffer.getBuffer()[(int)this.mPos];

            this.advPos(Byte.SIZE);
        }

        return tmpByte;
    }

    public short readInt16(short tmpShort)
    {
        if (this.canRead(Short.SIZE))
        {
            tmpShort = MBitConverter.ToInt16(this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

            this.advPos(Short.SIZE);
        }

        return tmpShort;
    }

    public short readUnsignedInt16(short tmpUshort)
    {
        if (this.canRead(Short.SIZE))
        {
            tmpUshort = MBitConverter.ToUInt16(this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

            this.advPos(Short.SIZE);
        }

        return tmpUshort;
    }

    public int readInt32(int tmpInt)
    {
        if (this.canRead(Integer.SIZE))
        {
            tmpInt = MBitConverter.ToInt32(this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

            this.advPos(Integer.SIZE);
        }

        return tmpInt;
    }

    public int readUnsignedInt32(int tmpUint)
    {
        if (this.canRead(Integer.SIZE))
        {
            // 如果字节序和本地字节序不同，需要转换
            tmpUint = MBitConverter.ToUInt32(this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

            this.advPos(Integer.SIZE);
        }

        return tmpUint;
    }

    public long readInt64(long tmpLong)
    {
        if (this.canRead(Long.SIZE))
        {
            tmpLong = MBitConverter.ToInt64(this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

            this.advPos(Long.SIZE);
        }

        return tmpLong;
    }

    public long readUnsignedInt64(long tmpUlong)
    {
        if (this.canRead(Long.SIZE))
        {
            tmpUlong = MBitConverter.ToUInt64(this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

            this.advPos(Long.SIZE);
        }

        return tmpUlong;
    }

    public float readFloat(float tmpFloat)
    {
        if (canRead(Float.SIZE))
        {
            if (this.mEndian != SystemEndian.msLocalEndian)
            {
                MArray.Copy(this.mDynBuffer.getBuffer(), (int)this.mPos, this.mReadFloatBytes, 0, Float.SIZE);
                MArray.Reverse(this.mReadFloatBytes, 0, Float.SIZE);
                tmpFloat = MBitConverter.ToFloat(this.mReadFloatBytes, (int)this.mPos);
            }
            else
            {
                tmpFloat = MBitConverter.ToFloat(this.mDynBuffer.getBuffer(), (int)this.mPos);
            }

            this.advPos(Float.SIZE);
        }

        return tmpFloat;
    }

    public double readDouble(double tmpDouble)
    {
        if (this.canRead(Double.SIZE))
        {
            if (mEndian != SystemEndian.msLocalEndian)
            {
                MArray.Copy(this.mDynBuffer.getBuffer(), (int)this.mPos, this.mReadDoubleBytes, 0, Double.SIZE);
                MArray.Reverse(this.mReadDoubleBytes, 0, Double.SIZE);
                tmpDouble = MBitConverter.ToDouble(this.mReadDoubleBytes, (int)this.mPos);
            }
            else
            {
                tmpDouble = MBitConverter.ToDouble(this.mDynBuffer.getBuffer(), (int)this.mPos);
            }

            this.advPos(Double.SIZE);
        }

        return tmpDouble;
    }

    //public MByteBuffer readMultiByte(ref string tmpStr, uint len, Encoding charSet)
    public MByteBuffer readMultiByte(String tmpStr, int len, GkEncode gkCharSet)
    {
        MEncoding charSet = UtilApi.convGkEncode2EncodingEncoding(gkCharSet);

        // 如果是 unicode ，需要大小端判断
        if (this.canRead(len))
        {
            tmpStr = charSet.GetString(this.mDynBuffer.getBuffer(), (int)this.mPos, (int)len);
            this.advPos(len);
        }

        return this;
    }

    // 这个是字节读取，没有大小端的区别
    public MByteBuffer readBytes(byte[] tmpBytes, int len)
    {
        if (this.canRead(len))
        {
            MArray.Copy(this.mDynBuffer.getBuffer(), (int)this.mPos, tmpBytes, 0, (int)len);
            this.advPos(len);
        }

        return this;
    }

    // 如果要使用 writeInt8 ，直接使用 writeMultiByte 这个函数
    public void writeInt8(char value)
    {
        if (!this.canWrite(Byte.SIZE))
        {
            this.extendDeltaCapicity(Byte.SIZE);
        }

        this.mDynBuffer.getBuffer()[this.mPos] = (byte)value;
        this.advPosAndLen(Byte.SIZE);
    }

    public void writeUnsignedInt8(byte value)
    {
        if (!this.canWrite(Byte.SIZE))
        {
            this.extendDeltaCapicity(Byte.SIZE);
        }

        this.mDynBuffer.getBuffer()[this.mPos] = value;
        this.advPosAndLen(Byte.SIZE);
    }

    public void writeInt16 (short value)
    {
        if (!this.canWrite(Short.SIZE))
        {
            this.extendDeltaCapicity(Short.SIZE);
        }

        MBitConverter.GetBytes(value, this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

        this.advPosAndLen(Short.SIZE);
    }

    public void writeUnsignedInt16(short value)
    {
        if (!this.canWrite(Short.SIZE))
        {
            this.extendDeltaCapicity(Short.SIZE);
        }

        MBitConverter.GetBytes(value, this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

        this.advPosAndLen(Short.SIZE);
    }

    public void writeInt32(int value)
    {
        if (!this.canWrite(Integer.SIZE))
        {
            this.extendDeltaCapicity(Integer.SIZE);
        }

        MBitConverter.GetBytes(value, this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

        this.advPosAndLen(Integer.SIZE);
    }

    public void writeUnsignedInt32 (int value)
    {
        this.writeUnsignedInt32(value, true);
    }

    public void writeUnsignedInt32 (int value, boolean bchangeLen)
    {
        if (!this.canWrite(Integer.SIZE))
        {
            this.extendDeltaCapicity(Integer.SIZE);
        }

        MBitConverter.GetBytes(value, this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

        if (bchangeLen)
        {
            this.advPosAndLen(Integer.SIZE);
        }
        else
        {
            this.advPos(Integer.SIZE);
        }
    }

    public void writeInt64(long value)
    {
        if (!this.canWrite(Long.SIZE))
        {
            this.extendDeltaCapicity(Long.SIZE);
        }

        MBitConverter.GetBytes(value, this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

        this.advPosAndLen(Long.SIZE);
    }

    public void writeUnsignedInt64(long value)
    {
        if (!this.canWrite(Long.SIZE))
        {
            this.extendDeltaCapicity(Long.SIZE);
        }

        MBitConverter.GetBytes(value, this.mDynBuffer.getBuffer(), (int)this.mPos, this.mEndian);

        this.advPosAndLen(Long.SIZE);
    }

    public void writeFloat(float value)
    {
        if (!this.canWrite(Float.SIZE))
        {
            this.extendDeltaCapicity(Float.SIZE);
        }

        this.mWriteFloatBytes = MBitConverter.GetBytes(value);
        if (this.mEndian != SystemEndian.msLocalEndian)
        {
            MArray.Reverse(this.mWriteFloatBytes);
        }
        MArray.Copy(this.mWriteFloatBytes, 0, this.mDynBuffer.getBuffer(), this.mPos, Float.SIZE);

        this.advPosAndLen(Float.SIZE);
    }

    public void writeDouble(double value)
    {
        if (!this.canWrite(Double.SIZE))
        {
            this.extendDeltaCapicity(Double.SIZE);
        }

        this.mWriteDoubleBytes = MBitConverter.GetBytes(value);
        if (this.mEndian != SystemEndian.msLocalEndian)
        {
            MArray.Reverse(this.mWriteDoubleBytes);
        }
        MArray.Copy(this.mWriteDoubleBytes, 0, this.mDynBuffer.getBuffer(), this.mPos, Double.SIZE);

        this.advPosAndLen(Double.SIZE);
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
            if (!this.canWrite(len))
            {
                this.extendDeltaCapicity(len);
            }
            MArray.Copy(value, start, this.mDynBuffer.getBuffer(), this.mPos, len);
            if (bchangeLen)
            {
                this.advPosAndLen(len);
            }
            else
            {
                this.advPos(len);
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

            if (!this.canWrite((int)len))
            {
                this.extendDeltaCapicity((int)len);
            }

            if (num < len)
            {
                MArray.Copy(charSet.GetBytes(value), 0, this.mDynBuffer.getBuffer(), this.mPos, num);
                // 后面补齐 0
                MArray.Clear(this.mDynBuffer.getBuffer(), (int)(this.mPos + num), len - num);
            }
            else
            {
                MArray.Copy(charSet.GetBytes(value), 0, this.mDynBuffer.getBuffer(), this.mPos, len);
            }
            this.advPosAndLen((int)len);
        }
        else
        {
            if (!this.canWrite((int)len))
            {
                this.extendDeltaCapicity((int)len);
            }

            this.advPosAndLen((int)len);
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
            writeBytes(this.mDynBuffer.getBuffer(), destStartPos + destlen_, lastLeft, false);          // 这个地方自己区域覆盖自己区域，可以保证自己不覆盖自己区域
        }

        this.setPosition(destStartPos);
        this.writeBytes(srcBytes, srcStartPos, srclen_, false);
    }

    public void insertUnsignedInt32(int value)
    {
        this.setLength(this.getLength() + Integer.SIZE);       // 扩大长度
        this.writeUnsignedInt32(value);     // 写入
    }

    public MByteBuffer readUnsignedLongByOffset(long tmpUlong, int offset)
    {
        this.setPosition(offset);
        this.readUnsignedInt64(tmpUlong);
        return this;
    }

    // 写入 EOF 结束符
    public void end()
    {
        this.mDynBuffer.getBuffer()[this.getLength()] = 0;
    }

    public MByteBuffer readBoolean(boolean tmpBool)
    {
        if (this.canRead(Byte.SIZE))
        {
            tmpBool = MBitConverter.ToBoolean(this.mDynBuffer.getBuffer(), (int)this.mPos);
            this.advPos(Byte.SIZE);
        }

        return this;
    }
}