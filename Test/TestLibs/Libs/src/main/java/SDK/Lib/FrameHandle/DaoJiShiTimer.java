package SDK.Lib.FrameHandle;

/**
 * @brief 倒计时定时器
 */
public class DaoJiShiTimer extends TimerItemBase
{
    @Override
    public void setTotalTime(float value)
    {
        super.setTotalTime(value);
        this.mCurRunTime = value;
    }

    @Override
    public float getRunTime()
    {
        return this.mTotalTime - this.mCurRunTime;
    }

    // 如果要获取剩余的倒计时时间，使用 getLeftCallTime
    @Override
    public float getLeftRunTime()
    {
        return this.mCurRunTime;
    }

    @Override
    public void OnTimer(float delta)
    {
        if (this.mDisposed)
        {
            return;
        }

        this.mCurRunTime -= delta;
        if(this.mCurRunTime < 0)
        {
            this.mCurRunTime = 0;
        }
        this.mIntervalLeftTime += delta;

        if (this.mIsInfineLoop)
        {
            checkAndDisp();
        }
        else
        {
            if (this.mCurRunTime <= 0)
            {
                disposeAndDisp();
            }
            else
            {
                checkAndDisp();
            }
        }
    }

    @Override
    public void reset()
    {
        this.mCurRunTime = this.mTotalTime;
        this.mCurCallTime = 0;
        this.mIntervalLeftTime = 0;
        this.mDisposed = false;
    }
}