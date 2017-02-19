package SDK.Lib.FrameHandle;

import SDK.Lib.FrameWork.Ctx;

public class SystemTimeData
{
    protected long mPreTime;           // 上一次更新时的秒数
    protected long mCurTime;           // 正在获取的时间
    protected float mDeltaSec;         // 两帧之间的间隔
    protected boolean mIsFixFrameRate;     // 固定帧率
    protected float mFixFrameRate;       // 固定帧率
    protected float mScale;             // delta 缩放

    // Edit->Project Setting->time
    protected float mFixedTimestep;

    public SystemTimeData()
    {
        this.mPreTime = 0;
        this.mCurTime = 0;
        this.mDeltaSec = 0.0f;
        this.mIsFixFrameRate = false;
        this.mFixFrameRate = 0.0417f;       //  1 / 24;
        this.mScale = 1;

        this.mFixedTimestep = 0.02f;
    }

    public void init()
    {

    }

    public void dispose()
    {

    }

    public float getDeltaSec()
    {
        return this.mDeltaSec;
    }

    public void setDeltaSec(float value)
    {
        this.mDeltaSec = value;
    }

    public float getFixedTimestep()
    {
        if (Ctx.mInstance.mCfg.mIsActorMoveUseFixUpdate)
        {
            return this.mFixedTimestep;
        }
        else
        {
            return this.mDeltaSec;
        }
    }

    public long getCurTime()
    {
        return this.mCurTime;
    }

    public void setCurTime(long value)
    {
        this.mCurTime = value;
    }

    public void nextFrame()
    {
        this.mPreTime = this.mCurTime;
        this.mCurTime = System.currentTimeMillis()/1000;

        if (mIsFixFrameRate)
        {
            this.mDeltaSec = this.mFixFrameRate;        // 每秒 24 帧
        }
        else
        {
            if (this.mPreTime != 0f)     // 第一帧跳过，因为这一帧不好计算间隔
            {
                this.mDeltaSec = (float)(this.mCurTime - this.mPreTime);
            }
            else
            {
                this.mDeltaSec = this.mFixFrameRate;        // 每秒 24 帧
            }
        }

        this.mDeltaSec *= this.mScale;
    }
}