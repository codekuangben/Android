package SDK.Lib.FrameHandle;

/**
 * @brief 逻辑心跳管理器
 */
public class LogicTickMgr extends TickMgr
{
    protected TimeInterval mTimeInterval;

    public LogicTickMgr()
    {
        this.mTimeInterval = new TimeInterval();
    }

    @Override
    protected void onExecAdvance(float delta)
    {
        if(this.mTimeInterval.canExec(delta))
        {
            super.onExecAdvance(delta);
        }
    }
}