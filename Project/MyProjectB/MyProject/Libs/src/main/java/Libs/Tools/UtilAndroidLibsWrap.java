package Libs.Tools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        Intent intent = new Intent(packageAndClassPath);
        activity.startActivity(intent);
    }

    // http://www.baidu.com 不是 www.baidu.com
    static public void openURL(Activity activity, String url)
    {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    static public void exit()
    {
        System.exit(0);
    }

    static public void finishActivity(Activity activity)
    {
        activity.finish();
    }
}