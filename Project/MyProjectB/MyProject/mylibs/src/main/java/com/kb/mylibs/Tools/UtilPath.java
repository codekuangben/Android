package com.kb.mylibs.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

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
    public static void setStringSharedPreferences(Context context, String key, String value)
    {
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

    public static void putSharedPreferencesByPreferenceManager(Context context, String key, String value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void putSharedPreferencesByActivity(Activity activity, String key, String value)
    {
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    // https://www.w3cschool.cn/android_training_course/android_training_course-9e6r27e9.html
    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }

        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state) ||
            Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return true;
        }

        return false;
    }

    public static File getExternalStoragePublicRootDirectory()
    {
        return Environment.getExternalStoragePublicDirectory(null);
    }

    /**
     * @url https://www.w3cschool.cn/android_training_course/android_training_course-9e6r27e9.html
     * Note: 当用户卸载我们的app时，android系统会删除以下文件：
     * 所有保存到internal storage的文件。
     * 所有使用getExternalFilesDir()方式保存在external storage的文件。
     * 然而，通常来说，我们应该手动删除所有通过 getCacheDir() 方式创建的缓存文件，以及那些不会再用到的文件。
     */
    public static File getExternalStoragePrivateRootDirectory(Context context)
    {
        return context.getExternalFilesDir(null);
    }

    public static File getPublicAlbumStorageDir(String albumName)
    {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        if (!file.mkdirs())
        {
            Log.e("Default", "Directory not created");
        }

        return file;
    }

    public static File getPrivateAlbumStorageDir(Context context, String albumName)
    {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);

        if (!file.mkdirs())
        {
            Log.e("Default", "Directory not created");
        }

        return file;
    }
}