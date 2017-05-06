package SDK.Lib.FrameHandle;

public class SystemFrameData
{
    protected int mTotalFrameCount;       // 总帧数
    protected int mCurFrameCount;         // 当前帧数
    protected float mCurTime;          // 当前一秒内时间
    protected int mFps;                // 帧率

    public void init()
    {

    }

    public void dispose()
    {

    }

    public int getTotalFrameCount()
    {
        return this.mTotalFrameCount;
    }

    public void nextFrame(float delta)
    {
        ++this.mTotalFrameCount;
        ++this.mCurFrameCount;
        this.mCurTime += delta;

        if(this.mCurTime > 1.0f)
        {
            this.mFps = (int)(this.mCurFrameCount / this.mCurTime);
            this.mCurFrameCount = 0;
            this.mCurTime = 0;
        }
    }
}