/**
* @brief 系统循环
*/
package com.kb.mylibs.FrameWork;

public class ProcessSys
{
    public ProcessSys()
    {

    }

    public void ProcessNextFrame()
    {
        Ctx.msIns.mSystemTimeData.nextFrame();
        this.Advance(Ctx.msIns.mSystemTimeData.getDeltaSec());
    }

    public void Advance(float delta)
    {
        Ctx.msIns.mSystemFrameData.nextFrame(delta);
        Ctx.msIns.mTickMgr.Advance(delta);            // 心跳
        Ctx.msIns.mTimerMgr.Advance(delta);           // 定时器
        Ctx.msIns.mFrameTimerMgr.Advance(delta);      // 帧定时器
    }

    public void ProcessNextFixedFrame()
    {
        this.FixedAdvance(Ctx.msIns.mSystemTimeData.getFixedTimestep());
    }

    public void FixedAdvance(float delta)
    {
        Ctx.msIns.mFixedTickMgr.Advance(delta);
    }
}