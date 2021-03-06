package com.kb.mylibs.Tools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @brief 对 Android 的进一步 wrap
 */
public class UtilAndroidLibsWrap
{
    static public View findViewById(Activity activity, int id)
    {
        View ret = null;

        ret = activity.findViewById(id);

        return ret;
    }

    static public View findViewById(View view, int id)
    {
        View ret = null;

        ret = view.findViewById(id);

        return ret;
    }

    public static void addEventHandle(View view, View.OnClickListener clickHandle)
    {
        if (null != view && null != clickHandle)
        {
            view.setOnClickListener(clickHandle);
        }
    }

    /**
     * @param packageAndClassPath = com.bbb.aaa.myapp.NetActivity
     * @brief 需要在 AndroidManifest.xml 添加
     * <intent-filter>
            <action android:name="com.bbb.aaa.myapp.NetActivity"/>
            <category android:name="android.intent.category.DEFAULT" />
        </intent-filter>
     */
    static public void startActivity(Activity activity, String packageAndClassPath)
    {
        if (null != activity)
        {
            Intent intent = new Intent(packageAndClassPath);
            activity.startActivity(intent);
        }
    }

    // http://www.baidu.com 不是 www.baidu.com
    static public void openURL(Activity activity, String url)
    {
        if (null != activity)
        {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
        }
    }

    static public void exit()
    {
        System.exit(0);
    }

    static public void finishActivity(Activity activity)
    {
        if (null != activity)
        {
            activity.finish();
        }
    }

    /**
     * @brief android获取cpu核数
     * @url https://www.jianshu.com/p/695b8345d870
     * @url https://www.cnblogs.com/garfieldx/p/3408678.html
     */
    public static int getDeviceCpuNum()
    {
        return Runtime.getRuntime().availableProcessors();
    }
}