package com.kb.App.Frame;

import com.kb.App.Test.TestBasic.TestMain;
import com.kb.mylibs.Core.GObject;
import com.kb.mylibs.FrameWork.Ctx;

/**
 *
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
        Ctx.mInstance.init();

        // 初始化 App
        ToolSetSys.instance();
        ToolSetSys.mInstance.init();

        // 单元测试
        TestMain testMain = new TestMain();
        testMain.run();
    }

    public  void dispose()
    {
        Ctx.mInstance.dispose();
        Ctx.mInstance = null;
    }
}