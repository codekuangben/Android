package SDK.Lib.Log;

import SDK.Lib.DataStruct.LockList;
import SDK.Lib.DataStruct.MList;
import SDK.Lib.FrameWork.MacroDef;
import SDK.Lib.Thread.MThread;
import SDK.Lib.Tools.TClassOp;
import SDK.Lib.Tools.UtilApi;

public class LogSys
{
    protected LockList<String> mAsyncLogList;              // 这个是多线程访问的
    protected LockList<String> mAsyncWarnList;            // 这个是多线程访问的
    protected LockList<String> mAsyncErrorList;          // 这个是多线程访问的

    protected String mTmpStr;

    protected MList<LogDeviceBase> mLogDeviceList;
    protected MList<LogTypeId>[] mEnableLogTypeList;

    protected boolean[] mEnableLog;    // 全局开关
    protected boolean[] mIsOutStack;     // 是否显示堆栈信息
    protected boolean[] mIsOutTimeStamp;   // 是否有时间戳

    // 构造函数仅仅是初始化变量，不涉及逻辑
    public LogSys()
    {
        this.mAsyncLogList = new LockList<String>("Logger_asyncLogList");
        this.mAsyncWarnList = new LockList<String>("Logger_asyncWarnList");
        this.mAsyncErrorList = new LockList<String>("Logger_asyncErrorList");
        this.mLogDeviceList = new MList<LogDeviceBase>();

        //this.mEnableLogTypeList = new MList<LogTypeId>[LogColor.eLC_Count.ordinal();
        Class classT = MList.class;
        this.mEnableLogTypeList = (MList<LogTypeId>[])TClassOp.createArray(classT, LogColor.eLC_Count.ordinal());
        this.mEnableLogTypeList[LogColor.eLC_LOG.ordinal()] = new MList<LogTypeId>();
        this.mEnableLogTypeList[LogColor.eLC_LOG.ordinal()].Add(LogTypeId.eLogCommon);

        this.mEnableLogTypeList[LogColor.eLC_WARN.ordinal()] = new MList<LogTypeId>();
        this.mEnableLogTypeList[LogColor.eLC_ERROR.ordinal()] = new MList<LogTypeId>();

        this.mEnableLog = new boolean[LogColor.eLC_Count.ordinal()];
        this.mEnableLog[(int)LogColor.eLC_LOG.ordinal()] = true;
        this.mEnableLog[(int)LogColor.eLC_WARN.ordinal()] = false;
        this.mEnableLog[(int)LogColor.eLC_ERROR.ordinal()] = false;

        this.mIsOutStack = new boolean[(int)LogColor.eLC_Count.ordinal()];
        this.mIsOutStack[(int)LogColor.eLC_LOG.ordinal()] = false;
        this.mIsOutStack[(int)LogColor.eLC_WARN.ordinal()] = false;
        this.mIsOutStack[(int)LogColor.eLC_ERROR.ordinal()] = false;

        this.mIsOutTimeStamp = new boolean[(int)LogColor.eLC_Count.ordinal()];
        this.mIsOutStack[(int)LogColor.eLC_LOG.ordinal()] = false;
        this.mIsOutStack[(int)LogColor.eLC_WARN.ordinal()] = false;
        this.mIsOutStack[(int)LogColor.eLC_ERROR.ordinal()] = false;
    }

    // 初始化逻辑处理
    public void init()
    {
        this.registerDevice();
        this.registerFileLogDevice();
    }

    // 析构
    public void dispose()
    {
        this.closeDevice();
    }

    public void setEnableLog(boolean value)
    {
        this.mEnableLog[(int)LogColor.eLC_LOG.ordinal()] = value;
    }

    public void setEnableWarn(boolean value)
    {
        this.mEnableLog[(int)LogColor.eLC_WARN.ordinal()] = value;
    }

    public void setEnableError(boolean value)
    {
        this.mEnableLog[(int)LogColor.eLC_ERROR.ordinal()] = value;
    }

    protected void registerDevice()
    {
        LogDeviceBase logDevice = null;

        if (MacroDef.ENABLE_WINLOG)
        {
            logDevice = new WinLogDevice();
            logDevice.initDevice();
            this.mLogDeviceList.Add(logDevice);
        }

        if (MacroDef.ENABLE_NETLOG)
        {
            logDevice = new NetLogDevice();
            logDevice.initDevice();
            this.mLogDeviceList.Add(logDevice);
        }
    }

    // 注册文件日志，因为需要账号，因此需要等待输入账号后才能注册，可能多次注册
    public void registerFileLogDevice()
    {

        if (MacroDef.ENABLE_FILELOG)
        {
            unRegisterFileLogDevice();

            LogDeviceBase logDevice = null;
            logDevice = new FileLogDevice();
            //((FileLogDevice)logDevice).fileSuffix = Ctx.mInstance.mDataPlayer.m_accountData.m_account;
            logDevice.initDevice();
            this.mLogDeviceList.Add(logDevice);
        }
    }

    protected void unRegisterFileLogDevice()
    {
        for(LogDeviceBase item : mLogDeviceList.list())
        {
            if(FileLogDevice.class == item.getClass())
            {
                item.closeDevice();
                this.mLogDeviceList.Remove(item);
                break;
            }
        }
    }

    protected boolean isInFilter(LogTypeId logTypeId, LogColor logColor)
    {
        if (this.mEnableLog[(int)logColor.ordinal()])
        {
            if (this.mEnableLogTypeList[(int)logColor.ordinal()].Contains(logTypeId))
            {
                return true;
            }

            return false;
        }

        return false;
    }

    public void log(String message)
    {
        this.log(message, LogTypeId.eLogCommon);
    }

    public void log(String message, LogTypeId logTypeId)
    {
        if (isInFilter(logTypeId, LogColor.eLC_LOG))
        {
            if(this.mIsOutTimeStamp[(int)LogColor.eLC_LOG.ordinal()])
            {
                message = String.format("{0}: {1}", UtilApi.getFormatTime(), message);
            }

            if (this.mIsOutStack[(int)LogColor.eLC_LOG.ordinal()])
            {
                Throwable ex = new Throwable();
                String traceStr = ex.toString();
                message = String.format("{0}\n{1}", message, traceStr);
            }

            if (MThread.isMainThread())
            {
                this.logout(message, LogColor.eLC_LOG);
            }
            else
            {
                this.asyncLog(message);
            }
        }
    }

    public void warn(String message)
    {
        this.warn(message, LogTypeId.eLogCommon);
    }

    public void warn(String message, LogTypeId logTypeId)
    {
        if (isInFilter(logTypeId, LogColor.eLC_WARN))
        {
            if (this.mIsOutTimeStamp[(int)LogColor.eLC_WARN.ordinal()])
            {
                message = String.format("{0}: {1}", UtilApi.getFormatTime(), message);
            }

            if (this.mIsOutStack[(int)LogColor.eLC_WARN.ordinal()])
            {
                Throwable ex = new Throwable();
                String traceStr = ex.toString();
                message = String.format("{0}\n{1}", message, traceStr);
            }

            if (MThread.isMainThread())
            {
                this.logout(message, LogColor.eLC_WARN);
            }
            else
            {
                this.asyncWarn(message);
            }
        }
    }

    public void error(String message)
    {
        this.error(message, LogTypeId.eLogCommon);
    }

    public void error(String message, LogTypeId logTypeId)
    {
        if (isInFilter(logTypeId, LogColor.eLC_ERROR))
        {
            if (this.mIsOutTimeStamp[(int)LogColor.eLC_ERROR.ordinal()])
            {
                message = String.format("{0}: {1}", UtilApi.getFormatTime(), message);
            }

            if (this.mIsOutStack[(int)LogColor.eLC_ERROR.ordinal()])
            {
                Throwable ex = new Throwable();
                String traceStr = ex.toString();
                message = String.format("{0}\n{1}", message, traceStr);
            }

            if (MThread.isMainThread())
            {
                this.logout(message, LogColor.eLC_ERROR);
            }
            else
            {
                this.asyncError(message);
            }
        }
    }

    // 多线程日志
    protected void asyncLog(String message)
    {
        mAsyncLogList.Add(message);
    }

    // 多线程日志
    protected void asyncWarn(String message)
    {
        this.mAsyncWarnList.Add(message);
    }

    // 多线程日志
    protected void asyncError(String message)
    {
        this.mAsyncErrorList.Add(message);
    }

    public void logout(String message)
    {
        this.logout(message, LogColor.eLC_LOG);
    }

    public void logout(String message, LogColor type)
    {
        if (MacroDef.THREAD_CALLCHECK)
        {
            MThread.needMainThread();
        }

        //foreach (LogDeviceBase logDevice in mLogDeviceList.list())
        int idx = 0;
        int len = this.mLogDeviceList.Count();
        LogDeviceBase logDevice = null;

        while (idx < len)
        {
            logDevice = this.mLogDeviceList.get(idx);
            logDevice.logout(message, type);

            ++idx;
        }
    }

    public void updateLog()
    {
        if (MacroDef.THREAD_CALLCHECK)
        {
            MThread.needMainThread();
        }

        while ((this.mTmpStr = mAsyncLogList.RemoveAt(0)) != "")
        {
            this.logout(mTmpStr, LogColor.eLC_LOG);
        }

        while ((this.mTmpStr = mAsyncWarnList.RemoveAt(0)) != "")
        {
            this.logout(mTmpStr, LogColor.eLC_WARN);
        }

        while ((this.mTmpStr = mAsyncErrorList.RemoveAt(0)) != "")
        {
            this.logout(mTmpStr, LogColor.eLC_ERROR);
        }
    }

    protected void closeDevice()
    {
        for(LogDeviceBase logDevice : mLogDeviceList.list())
        {
            logDevice.closeDevice();
        }
    }
}