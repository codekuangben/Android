package com.kb.mylibs.FrameWork;

import android.content.Context;

import com.kb.mylibs.FileVisitor.MAssetManager;
import com.kb.mylibs.FrameHandle.FixedTickMgr;
import com.kb.mylibs.FrameHandle.FrameTimerMgr;
import com.kb.mylibs.FrameHandle.ITickedObject;
import com.kb.mylibs.FrameHandle.LogicTickMgr;
import com.kb.mylibs.FrameHandle.ResizeMgr;
import com.kb.mylibs.FrameHandle.SystemFrameData;
import com.kb.mylibs.FrameHandle.SystemTimeData;
import com.kb.mylibs.FrameHandle.TickMgr;
import com.kb.mylibs.FrameHandle.TickPriority;
import com.kb.mylibs.FrameHandle.TimerMgr;
import com.kb.mylibs.Log.LogSys;
import com.kb.mylibs.MsgRoute.MsgRouteNotify;
import com.kb.mylibs.MsgRoute.SysMsgRoute;
import com.kb.mylibs.ObjectPool.IdPoolSys;
import com.kb.mylibs.ObjectPool.PoolSys;
import com.kb.mylibs.Task.TaskQueue;
import com.kb.mylibs.Task.TaskThreadPool;

/**
 * @brief 全局数据区
 */
public class Ctx
{
    public static Ctx msIns;

    public Config mCfg;                       // 整体配置文件
    public LogSys mLogSys;                    // 日志系统

    public TickMgr mTickMgr;                  // 心跳管理器
    public FixedTickMgr mFixedTickMgr;             // 固定间隔心跳管理器
    public LogicTickMgr mLogicTickMgr;        // 逻辑心跳管理器
    public ProcessSys mProcessSys;            // 游戏处理系统

    public TimerMgr mTimerMgr;                // 定时器系统
    public FrameTimerMgr mFrameTimerMgr;      // 定时器系统
    public ResizeMgr mResizeMgr;              // 窗口大小修改管理器
    public EngineLoop mEngineLoop;            // 引擎循环

    public ShareData mShareData;               // 共享数据系统
    public MsgRouteNotify mMsgRouteNotify;     // RouteMsg 客户端自己消息流程
    public FactoryBuild mFactoryBuild;         // 生成各种内容，上层只用接口

    public SystemSetting mSystemSetting;
    public PoolSys mPoolSys;
    public TaskQueue mTaskQueue;
    public TaskThreadPool mTaskThreadPool;

    public GlobalDelegate mGlobalDelegate;
    public IdPoolSys mIdPoolSys;
    public SysMsgRoute mSysMsgRoute;
    public SystemTimeData mSystemTimeData;
    public SystemFrameData mSystemFrameData;
    public MAssetManager mAssetManager;
    public Context mContext;

    public Ctx()
    {

    }

    public static Ctx instance()
    {
        if (msIns == null)
        {
            msIns = new Ctx();
        }
        return msIns;
    }

    protected void constructInit()
    {
        this.mMsgRouteNotify = new MsgRouteNotify();
        this.mSystemSetting = new SystemSetting();
        this.mPoolSys = new PoolSys();
        this.mTaskQueue = new TaskQueue("TaskQueue");
        this.mTaskThreadPool = new TaskThreadPool();

        this.mCfg = new Config();
        this.mFactoryBuild = new FactoryBuild();

        this.mProcessSys = new ProcessSys();
        this.mTickMgr = new TickMgr();
        this.mFixedTickMgr = new FixedTickMgr();
        this.mTimerMgr = new TimerMgr();
        this.mFrameTimerMgr = new FrameTimerMgr();

        this.mShareData = new ShareData();
        this.mEngineLoop = new EngineLoop();
        this.mResizeMgr = new ResizeMgr();

        this.mLogSys = new LogSys();
        this.mGlobalDelegate = new GlobalDelegate();
        this.mIdPoolSys = new IdPoolSys();

        this.mLogicTickMgr = new LogicTickMgr();
        this.mSysMsgRoute = new SysMsgRoute("");
        this.mSystemTimeData = new SystemTimeData();
        this.mSystemFrameData = new SystemFrameData();
        this.mAssetManager = new MAssetManager();
    }

    public void logicInit()
    {
        this.mLogSys.init();
        this.mTickMgr.init();
        this.mFixedTickMgr.init();

        this.mTaskQueue.mTaskThreadPool = this.mTaskThreadPool;
        this.mTaskThreadPool.initThreadPool(2, this.mTaskQueue);

        this.mGlobalDelegate.init();
        this.mResizeMgr.init();
        this.mIdPoolSys.init();
        this.mLogicTickMgr.init();
        this.mSysMsgRoute.init();
        this.mSystemTimeData.init();

        this.mSystemFrameData.init();
        this.mAssetManager.init();

        this.addEventHandle();
    }

    public void init()
    {
        // 构造初始化
        constructInit();
        // 逻辑初始化，交叉引用的对象初始化
        logicInit();
    }

    public void dispose()
    {
        if (null != this.mResizeMgr)
        {
            this.mResizeMgr.dispose();
            this.mResizeMgr = null;
        }
        if (null != this.mTickMgr)
        {
            this.mTickMgr.dispose();
            this.mTickMgr = null;
        }
        if (null != this.mFixedTickMgr)
        {
            this.mFixedTickMgr.dispose();
            this.mFixedTickMgr = null;
        }

        // 关闭日志设备
        if (null != this.mLogSys)
        {
            this.mLogSys.dispose();
            this.mLogSys = null;
        }
        if(null != this.mIdPoolSys)
        {
            this.mIdPoolSys.dispose();
            this.mIdPoolSys = null;
        }
        if(null != this.mLogicTickMgr)
        {
            this.mLogicTickMgr.dispose();
            this.mLogicTickMgr = null;
        }
        if(null != this.mSysMsgRoute)
        {
            this.mSysMsgRoute.dispose();
            this.mSysMsgRoute = null;
        }
        if(null != this.mSystemTimeData)
        {
            this.mSystemTimeData.dispose();
            this.mSystemTimeData = null;
        }
        if(null != this.mSystemFrameData)
        {
            this.mSystemFrameData.dispose();
            this.mSystemFrameData = null;
        }
        if (null != this.mAssetManager)
        {
            this.mAssetManager.dispose();
            this.mAssetManager = null;
        }
    }

    public void quitApp()
    {
        this.dispose();

        // 释放自己
        //msIns = null;
    }

    protected void addEventHandle()
    {
        this.mTickMgr.addTick((ITickedObject)this.mResizeMgr, TickPriority.eTPResizeMgr);
    }

    public void initNativeContext(Context context)
    {
        this.mContext = context;

        if (null != this.mAssetManager)
        {
            this.mAssetManager.setContext(this.mContext);
        }
    }
}