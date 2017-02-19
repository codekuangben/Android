package SDK.Lib.FrameHandle;

import SDK.Lib.DelayHandle.IDelayHandleItem;
import SDK.Lib.EventHandle.IDispatchObject;

/**
 * @brief 定时器，这个是不断增长的
 */
public class TimerItemBase implements IDelayHandleItem, IDispatchObject
{
    public float mInternal;        // 定时器间隔
    public float mTotalTime;       // 总共定时器时间
    public float mCurRunTime;      // 当前定时器运行的时间
    public float mCurCallTime;     // 当前定时器已经调用的时间
    public boolean mIsInfineLoop;      // 是否是无限循环
    public float mIntervalLeftTime;     // 定时器调用间隔剩余时间
    public TimerFunctionObject mTimerDisp;       // 定时器分发
    public boolean mDisposed;             // 是否已经被释放
    public boolean mIsContinuous;          // 是否是连续的定时器

    public TimerItemBase()
    {
        this.mInternal = 1;
        this.mTotalTime = 1;
        this.mCurRunTime = 0;
        this.mCurCallTime = 0;
        this.mIsInfineLoop = false;
        this.mIntervalLeftTime = 0;
        this.mTimerDisp = new TimerFunctionObject();
        this.mDisposed = false;
        this.mIsContinuous = false;
    }

    public void setFuncObject(ICalleeObjectTimer handle)
    {
        this.mTimerDisp.setFuncObject(handle);
    }

    public void setTotalTime(float value)
    {
        this.mTotalTime = value;
    }

    public float getRunTime()
    {
        return this.mCurRunTime;
    }

    public float getCallTime()
    {
        return this.mCurCallTime;
    }

    public float getLeftRunTime()
    {
        return this.mTotalTime - this.mCurRunTime;
    }

    public float getLeftCallTime()
    {
        return this.mTotalTime - this.mCurCallTime;
    }

    // 在调用回调函数之前处理
    protected void onPreCallBack()
    {

    }

    public void OnTimer(float delta)
    {
        if (this.mDisposed)
        {
            return;
        }

        this.mCurRunTime += delta;
        if (this.mCurRunTime > this.mTotalTime)
        {
            this.mCurRunTime = this.mTotalTime;
        }
        this.mIntervalLeftTime += delta;

        if (this.mIsInfineLoop)
        {
            checkAndDisp();
        }
        else
        {
            if (this.mCurRunTime >= this.mTotalTime)
            {
                disposeAndDisp();
            }
            else
            {
                checkAndDisp();
            }
        }
    }

    public void disposeAndDisp()
    {
        if (this.mIsContinuous)
        {
            this.continueDisposeAndDisp();
        }
        else
        {
            this.discontinueDisposeAndDisp();
        }
    }

    protected void continueDisposeAndDisp()
    {
        this.mDisposed = true;

        while (this.mIntervalLeftTime >= this.mInternal && this.mCurCallTime < this.mTotalTime)
        {
            this.mCurCallTime = this.mCurCallTime + this.mInternal;
            this.mIntervalLeftTime = this.mIntervalLeftTime - this.mInternal;
            this.onPreCallBack();

            if (this.mTimerDisp.isValid())
            {
                this.mTimerDisp.call(this);
            }
        }
    }

    protected void discontinueDisposeAndDisp()
    {
        this.mDisposed = true;
        this.mCurCallTime = this.mTotalTime;
        this.onPreCallBack();

        if (this.mTimerDisp.isValid())
        {
            this.mTimerDisp.call(this);
        }
    }

    public void checkAndDisp()
    {
        if(this.mIsContinuous)
        {
            continueCheckAndDisp();
        }
        else
        {
            discontinueCheckAndDisp();
        }
    }

    // 连续的定时器
    protected void continueCheckAndDisp()
    {
        while (this.mIntervalLeftTime >= this.mInternal)
        {
            // 这个地方 m_curCallTime 肯定会小于 m_totalTime，因为在调用这个函数的外部已经进行了判断
            this.mCurCallTime = this.mCurCallTime + this.mInternal;
            this.mIntervalLeftTime = this.mIntervalLeftTime - this.mInternal;
            this.onPreCallBack();

            if (this.mTimerDisp.isValid())
            {
                this.mTimerDisp.call(this);
            }
        }
    }

    // 不连续的定时器
    protected void discontinueCheckAndDisp()
    {
        if (this.mIntervalLeftTime >= this.mInternal)
        {
            // 这个地方 m_curCallTime 肯定会小于 m_totalTime，因为在调用这个函数的外部已经进行了判断
            this.mCurCallTime = this.mCurCallTime + (((int)(this.mIntervalLeftTime / this.mInternal)) * this.mInternal);
            this.mIntervalLeftTime = this.mIntervalLeftTime % this.mInternal;   // 只保留余数
            this.onPreCallBack();

            if (this.mTimerDisp.isValid())
            {
                this.mTimerDisp.call(this);
            }
        }
    }

    public void reset()
    {
        this.mCurRunTime = 0;
        this.mCurCallTime = 0;
        this.mIntervalLeftTime = 0;
        this.mDisposed = false;
    }

    public void setClientDispose(boolean isDispose)
    {

    }

    public boolean isClientDispose()
    {
        return false;
    }

    public void startTimer()
    {
        //Ctx.mInstance.mTimerMgr.addTimer(this);
    }

    public void stopTimer()
    {
        //Ctx.mInstance.mTimerMgr.removeTimer(this);
    }
}