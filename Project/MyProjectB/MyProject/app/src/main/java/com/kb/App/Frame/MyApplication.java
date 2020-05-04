package com.kb.App.Frame;

import android.app.Application;
import android.content.Context;

import com.kb.mylibs.FrameWork.Ctx;

/**
 * @url https://www.cnblogs.com/hello-studio/p/9640787.html
 * @brief Android 获取静态上下文（Application）
 */
public class MyApplication extends Application
{
    private static Context _Context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        MyApplication._Context = this.getApplicationContext();
        //Context context = this.getApplicationContext();
        //Ctx.mInstance.initNativeContext(context);
    }

    public static Context getAppContext()
    {
        return MyApplication._Context;
    }
}