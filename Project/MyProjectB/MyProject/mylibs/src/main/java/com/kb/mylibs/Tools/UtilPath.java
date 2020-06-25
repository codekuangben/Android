package com.kb.mylibs.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class UtilPath
{
    /**
     * @brief 输出logcat到文件中
     * @url https://www.csdn.net/gather_23/MtTakg3sOTU5NC1ibG9n.html
     */
    public static void saveLogcatToFile(Context context, String path, String fileName)
    {
        File outputFile = new File(path, fileName);
        try
        {
            Process process = Runtime.getRuntime().exec("logcat -f " + outputFile.getAbsolutePath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @brief Android数据保存到文件
     * @url https://www.w3cschool.cn/android_training_course/android_training_course-9e6r27e9.html
     * 编写:kesenhoo - 原文:http://developer.android.com/training/basics/data-storage/files.html
     */

    /**
     * @brief 将Android文件保存到外部存储
     * @url https://m.imooc.com/wenda/detail/567399
     */
    public static File getExternalStorageDirectory()
    {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * @brief Android文件存储(一)内部存储
     * @url https://blog.csdn.net/xy4_android/article/details/80985890
     * 这个方法返回一个绝对路径，这个绝对路径是有openFilePutput()方法创建的文件。重要的是最后一句，当在这个路径下调用程序来进行读写操作的时候，是不需要任何额外的权限的。也就是说，我们在使用内部存储的方法存储数据的时候，不需要用在manifest文件中声明权限，也不需要考虑android6.0的运行时权限的。想想还是很happy滴
     */
    public static File getFilesDir(Context context)
    {
        return context.getFilesDir();
    }

    public static FileOutputStream openFileOutput(Context context, String name, int mode)
    {
        try
        {
            return context.openFileOutput(name, mode);
        }
        catch (FileNotFoundException exception)
        {
            return null;
        }
    }

    public static File getCacheDir(Context context)
    {
        return context.getCacheDir();
    }

    public static File getDir(Context context, String name, int mode)
    {
        return context.getDir(name, mode);
    }

    /**
     * @url https://blog.csdn.net/xy4_android/article/details/80985890
     */
    public static void setStringSharedPreferences(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("Default", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void getStringSharedPreferences(Context context, String key, String value)
    {
        SharedPreferences sp = context.getSharedPreferences("Default", MODE_PRIVATE);
        value = sp.getString(key, "");
    }

    private void putSharedPreferencesByPreferenceManager(Context context, String key, String value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    private void putSharedPreferencesByActivity(Activity activity, String key, String value)
    {
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }
}