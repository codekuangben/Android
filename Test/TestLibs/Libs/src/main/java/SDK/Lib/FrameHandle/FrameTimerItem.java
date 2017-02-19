package SDK.Lib.FrameHandle;

import SDK.Lib.DelayHandle.IDelayHandleItem;

/**
 * @brief 定时器，这个是不断增长的
 */
public class FrameTimerItem implements IDelayHandleItem
{
    public int mInternal;              // 帧数间隔
    public int mTotalFrameCount;       // 总共次数
    public int mCurFrame;              // 当前已经调用的定时器的时间
    public int mCurLeftFrame;          // 剩余帧数
    public boolean mIsInfineLoop;      // 是否是无限循环
    public ICalleeObjectFrameTimer mTimerDisp;       // 定时器分发
    public boolean mDisposed;             // 是否已经被释放

    //protected int m_preFrame = 0;

    public FrameTimerItem()
    {
        this.mInternal = 1;
        this.mTotalFrameCount = 1;
        this.mCurFrame = 0;
        this.mIsInfineLoop = false;
        this.mCurLeftFrame = 0;
        this.mTimerDisp = null;
        this.mDisposed = false;
    }

    public void OnFrameTimer()
    {
        if (this.mDisposed)
        {
            return;
        }

        ++this.mCurFrame;
        ++this.mCurLeftFrame;

        //if (m_preFrame == m_curFrame)
        //{

        //}

        //m_curFrame = m_preFrame;

        if (this.mIsInfineLoop)
        {
            if (this.mCurLeftFrame == this.mInternal)
            {
                this.mCurLeftFrame = 0;

                if (this.mTimerDisp != null)
                {
                    this.mTimerDisp.call(this);
                }
            }
        }
        else
        {
            if (this.mCurFrame == this.mTotalFrameCount)
            {
                this.mDisposed = true;
                if (this.mTimerDisp != null)
                {
                    this.mTimerDisp.call(this);
                }
            }
            else
            {
                if (this.mCurLeftFrame == this.mInternal)
                {
                    this.mCurLeftFrame = 0;
                    if (this.mTimerDisp != null)
                    {
                        this.mTimerDisp.call(this);
                    }
                }
            }
        }
    }

    public void reset()
    {
        this.mCurFrame = 0;
        this.mCurLeftFrame = 0;
        this.mDisposed = false;
    }

    public void setClientDispose(boolean isDispose)
    {

    }

    public boolean isClientDispose()
    {
        return false;
    }
}