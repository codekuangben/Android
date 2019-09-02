package Libs.Tools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import Libs.EventHandle.IDispatchObject;
// 错误: 程序包android.support.v7.app不存在
// import android.support.v7.app.AppCompatActivity;

/**
 * @brief 对 api 的进一步 wrap
 */
public class UtilApi
{
    public static final String CR_LF = "\\r\\n";
    public static final String UNITY3D = "unity3d";

    static public MEncoding convGkEncode2EncodingEncoding(GkEncode gkEncode)
    {
        MEncoding retEncode = MEncoding.UTF8;

        if (GkEncode.eUTF8 == gkEncode)
        {
            retEncode = MEncoding.UTF8;
        }
        else if (GkEncode.eGB2312 == gkEncode)
        {
            retEncode = MEncoding.UTF8;
        }
        else if (GkEncode.eUnicode == gkEncode)
        {
            retEncode = MEncoding.Unicode;
        }
        else if (GkEncode.eDefault == gkEncode)
        {
            retEncode = MEncoding.Default;
        }

        return retEncode;
    }

    // 判断两个 GameObject 地址是否相等
    public static boolean isAddressEqual(Object a, Object b)
    {
        return a.equals(b);
    }

    // 判断两个函数是否相等，不能使用 isAddressEqual 判断函数是否相等
    public static boolean isDelegateEqual(IDispatchObject a, IDispatchObject b)
    {
        return a == b;
    }

    static public int getScreenWidth()
    {
        return 1000;
    }

    static public int getScreenHeight()
    {
        return 1000;
    }

    static public String getFormatTime()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    static public void printCallStack()
    {
        Throwable ex = new Throwable();

        StackTraceElement[] stackElements = ex.getStackTrace();

        if(stackElements != null)
        {
            for(int i = 0; i < stackElements.length; i++)
            {
                System.out.println(stackElements[i].getClassName());
                System.out.println(stackElements[i].getFileName());
                System.out.println(stackElements[i].getLineNumber());
                System.out.println(stackElements[i].getMethodName());
                System.out.println("-----------------------------------");
            }
        }
    }

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