/**
* @brief 系统循环
*/
package SDK.Lib.FrameWork;

import SDK.Lib.FrameHandle.TickMode;

public class ProcessSys
{
    public ProcessSys()
    {

    }

    public void ProcessNextFrame()
    {
        Ctx.mInstance.mSystemTimeData.nextFrame();
        this.Advance(Ctx.mInstance.mSystemTimeData.getDeltaSec());
    }

    public void Advance(float delta)
    {
        Ctx.mInstance.mSystemFrameData.nextFrame(delta);
        Ctx.mInstance.mTickMgr.Advance(delta, TickMode.eTM_Update);            // 心跳
        Ctx.mInstance.mTimerMgr.Advance(delta);           // 定时器
        Ctx.mInstance.mFrameTimerMgr.Advance(delta);      // 帧定时器
    }

    public void ProcessNextFixedFrame()
    {
        this.FixedAdvance(Ctx.mInstance.mSystemTimeData.getFixedTimestep());
    }

    public void FixedAdvance(float delta)
    {
        Ctx.mInstance.mFixedTickMgr.Advance(delta, TickMode.eTM_FixedUpdate);
    }
}