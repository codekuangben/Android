package AppFrame.App;

import AppFrame.Test.TestBasic.TestMain;
import Libs.Core.GObject;
import Libs.FrameWork.Ctx;

/**
 *
 */
public class MyApp extends GObject
{
    public MyApp()
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