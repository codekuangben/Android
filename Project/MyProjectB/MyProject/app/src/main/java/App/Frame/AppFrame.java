package App.Frame;

import App.Test.TestBasic.TestMain;
import Libs.Core.GObject;
import Libs.FrameWork.Ctx;

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