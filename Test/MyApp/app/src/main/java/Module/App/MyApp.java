package Module.App;

import SDK.Lib.Core.GObject;
import SDK.Lib.FrameWork.Ctx;

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
    }

    public  void dispose()
    {
        Ctx.mInstance.dispose();
        Ctx.mInstance = null;
    }
}