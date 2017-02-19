package SDK.Lib.FileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import SDK.Lib.Core.GObject;
import SDK.Lib.EventHandle.AddOnceAndCallOnceEventDispatch;
import SDK.Lib.EventHandle.IDispatchObject;
import SDK.Lib.Tools.GkEncode;
import SDK.Lib.Tools.MEncoding;
import SDK.Lib.Tools.UtilApi;

/**
 * @brief 仅支持本地文件操作，仅支持同步操作
 */
public class MFileStream extends GObject implements IDispatchObject
{
    public File mFile;
    public FileInputStream mFileInputStream;
    public FileOutputStream mFileOutputStream;

    protected String mFilePath;
    protected FileOpState mFileOpState;

    protected String mText;
    protected byte[] mBytes;

    /**
     * @brief 仅支持同步操作，目前无视参数 isSyncMode 和 evtDisp。FileMode.CreateNew 如果文件已经存在就抛出异常，FileMode.Append 和 FileAccess.Write 要同时使用
     */
    public MFileStream(String filePath)
    {
        this.mTypeId = "MFileStream";

        this.mFilePath = filePath;
        this.mFileOpState = FileOpState.eNoOp;

        this.checkAndOpen();
    }

    public void seek(long offset, MSeekOrigin origin)
    {
        if(this.mFileOpState == FileOpState.eOpenSuccess)
        {
            if(MSeekOrigin.Begin == origin)
            {
                try
                {
                    if(null != this.mFileInputStream)
                    {
                        this.mFileInputStream.getChannel().position(offset);
                    }

                    if(null != this.mFileOutputStream)
                    {
                        this.mFileOutputStream.getChannel().position(offset);
                    }
                }
                catch (Exception e)
                {

                }
            }
            else if(MSeekOrigin.End == origin)
            {
                try
                {
                    if(null != this.mFileInputStream)
                    {
                        this.mFileInputStream.getChannel().position(this.mFile.length() - 1 - offset);
                    }

                    if(null != this.mFileOutputStream)
                    {
                        this.mFileOutputStream.getChannel().position(this.mFile.length() - 1 - offset);
                    }
                }
                catch (Exception e)
                {

                }
            }
            else
            {
                try
                {
                    if(null != this.mFileInputStream)
                    {
                        this.mFileInputStream.getChannel().position(this.mFileInputStream.getChannel().position() + offset);
                    }

                    if(null != this.mFileOutputStream)
                    {
                        this.mFileOutputStream.getChannel().position(this.mFileOutputStream.getChannel().position() + offset);
                    }
                }
                catch (Exception e)
                {

                }
            }
        }
    }

    public void dispose()
    {
        this.close();
    }

    protected void checkAndOpen()
    {
        if(this.mFileOpState == FileOpState.eNoOp)
        {
            this.mFile = new File(this.mFilePath);
        }
    }

    protected void syncOpenFileStream()
    {
        if (this.mFileOpState == FileOpState.eNoOp)
        {
            this.mFileOpState = FileOpState.eOpening;

            try
            {
                this.mFile = new File(mFilePath);
                this.mFileOpState = FileOpState.eOpenSuccess;
            }
            catch(Exception exp)
            {
                this.mFileOpState = FileOpState.eOpenFail;
            }
        }
    }

    public boolean isValid()
    {
        return this.mFileOpState == FileOpState.eOpenSuccess;
    }

    // 获取总共长度
    public int getLength()
    {
        int len = 0;

        if (this.mFileOpState == FileOpState.eOpenSuccess)
        {
            if (this.mFile != null)
            {
                len = (int)this.mFile.length();
            }
            /*
            if (mFileStream != null && mFileStream.CanSeek)
            {
                try
                {
                    len = (int)mFileStream.Seek(0, SeekOrigin.End);     // 移动到文件结束，返回长度
                    len = (int)mFileStream.Position;                    // Position 移动到 Seek 位置
                }
                catch(Exception exp)
                {
                }
            }
            */
        }

        return len;
    }

    protected void close()
    {
        if (this.mFileOpState == FileOpState.eOpenSuccess)
        {
            if (this.mFile != null)
            {
                try
                {
                    if(null != this.mFileInputStream)
                    {
                        this.mFileInputStream.close();
                        this.mFileInputStream = null;
                    }

                    if(null != this.mFileInputStream)
                    {
                        this.mFileOutputStream.flush();

                        this.mFileOutputStream.close();
                        this.mFileOutputStream = null;
                    }
                }
                catch(Exception e)
                {

                }
            }

            this.mFileOpState = FileOpState.eOpenClose;
            this.mFileOpState = FileOpState.eNoOp;
        }
    }

    public String readText()
    {
        return this.readText(0, 0, null);
    }

    public String readText(int offset)
    {
        return this.readText(offset, 0, null);
    }

    public String readText(int offset, int count)
    {
        return this.readText(offset, count, null);
    }

    public String readText(int offset, int count, MEncoding encode)
    {
        this.checkAndOpen();

        String retStr = "";
        byte[] bytes = null;

        if (encode == null)
        {
            encode = MEncoding.UTF8;
        }

        if (count == 0)
        {
            count = getLength();
        }

        if (this.mFileOpState == FileOpState.eOpenSuccess)
        {
            if (this.mFile.canRead())
            {
                try
                {
                    bytes = new byte[count];
                    this.mFileInputStream.read(bytes, 0, count);

                    retStr = encode.GetString(bytes);
                }
                catch (Exception err)
                {

                }
            }
        }

        return retStr;
    }

    public byte[] readByte()
    {
        return this.readByte(0, 0);
    }

    public byte[] readByte(int offset)
    {
        return this.readByte(offset, 0);
    }

    public byte[] readByte(int offset, int count)
    {
        this.checkAndOpen();

        if (count == 0)
        {
            count = getLength();
        }

        byte[] bytes = null;

        if (this.mFile.canRead())
        {
            try
            {
                bytes = new byte[count];
                this.mFileInputStream.read(bytes, 0, count);
            }
            catch (Exception err)
            {

            }
        }

        return bytes;
    }

    public void writeText(String text)
    {
        this.writeText(text, GkEncode.eUTF8);
    }

    public void writeText(String text, GkEncode gkEncode)
    {
        MEncoding encode = UtilApi.convGkEncode2EncodingEncoding(gkEncode);

        this.checkAndOpen();

        if (this.mFile.canWrite())
        {
            //if (encode == null)
            //{
            //    encode = GkEncode.UTF8;
            //}

            byte[] bytes = encode.GetBytes(text);

            if (bytes != null)
            {
                try
                {
                    this.mFileOutputStream.write(bytes, 0, bytes.length);
                }
                catch (Exception err)
                {

                }
            }
        }
    }

    public void writeByte(byte[] bytes)
    {
        this.writeByte(bytes, 0, 0);
    }

    public void writeByte(byte[] bytes, int offset)
    {
        this.writeByte(bytes, offset, 0);
    }

    public void writeByte(byte[] bytes, int offset, int count)
    {
        this.checkAndOpen();

        if (this.mFile.canWrite())
        {
            if (bytes != null)
            {
                if (count == 0)
                {
                    count = bytes.length;
                }

                if (count != 0)
                {
                    try
                    {
                        this.mFileOutputStream.write(bytes, offset, count);
                    }
                    catch (Exception err)
                    {

                    }
                }
            }
        }
    }

    public void writeLine(String text)
    {
        this.writeLine(text, GkEncode.eUTF8);
    }

    public void writeLine(String text, GkEncode gkEncode)
    {
        text = text + UtilApi.CR_LF;
        writeText(text, gkEncode);
    }
}