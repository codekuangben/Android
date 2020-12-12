package com.kb.mylibs.FrameWork;

import com.kb.mylibs.MsgRoute.MsgRouteBase;

/**
 * @brief 主循环
 */
public class EngineLoop
{
    public void MainLoop()
    {
        // 每一帧处理
        // 处理 input
        //Ctx.msIns.mInputMgr.handleKeyBoard();

        // 处理客户端的各类消息
        // 处理客户端自己的消息机制
        MsgRouteBase routeMsg = null;
        while ((routeMsg = Ctx.msIns.mSysMsgRoute.pop()) != null)
        {
            Ctx.msIns.mMsgRouteNotify.handleMsg(routeMsg);
        }

        // 处理网络
        //if (!Ctx.msIns.mNetCmdNotify.isStopNetHandle)
        //{
        //    MByteBuffer ret = null;
        //    while ((ret = Ctx.msIns.mNetMgr.getMsg()) != null)
        //    {
        //        if (null != Ctx.msIns.mNetCmdNotify)
        //        {
        //            Ctx.msIns.mNetCmdNotify.addOneHandleMsg();
        //            Ctx.msIns.mNetCmdNotify.handleMsg(ret);       // CS 中处理
        //            Ctx.msIns.mLuaSystem.receiveToLuaRpc(ret);    // Lua 中处理
        //        }
        //    }
        //}

        // 填充数据到 KBEngine ，使用 KBEngine 引擎的逻辑解析
        //if (!Ctx.msIns.mNetCmdNotify.isStopNetHandle)
        //{
        //    MByteBuffer ret = null;
        //    while ((ret = Ctx.msIns.mNetMgr.getMsg_KBE()) != null)
        //    {
        //        Ctx.msIns.mMKBEMainEntry.gameapp.pushBuffer(ret.dynBuffer.buffer, ret.dynBuffer.size);
        //    }
        //}

        // KBEngine 引擎逻辑处理
        //Ctx.msIns.mMKBEMainEntry.FixedUpdate();

        // 每一帧的游戏逻辑处理
        Ctx.msIns.mProcessSys.ProcessNextFrame();
        // 日志处理
        Ctx.msIns.mLogSys.updateLog();
    }

    public void fixedUpdate()
    {
        Ctx.msIns.mProcessSys.ProcessNextFixedFrame();
    }
}