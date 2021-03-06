package com.kb.Module.Frame;

import com.kb.Test.TestBase.TestMain;
import com.kb.mylibs.Core.GObject;
import com.kb.mylibs.FrameWork.Ctx;

/**
 * @brief AppFrame 基本框架
 */
public class AppFrame extends GObject
{
    public AppFrame()
    {

    }

    public void init()
    {
        // 初始化核心功能
        Ctx.instance();
        Ctx.msIns.init();

        // 初始化 App
        //ToolKitSys.instance();
        //ToolKitSys.msIns.init();

        // 单元测试
        TestMain testMain = new TestMain();
        testMain.run();
    }

    public  void dispose()
    {
        Ctx.msIns.dispose();
        Ctx.msIns = null;
    }
}