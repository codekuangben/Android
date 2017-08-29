package SDK.Lib.NetWork;

import android.content.Intent;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ref http://blog.csdn.net/qq_32452623/article/details/52280912
 */

public class DownloadPackage
{
	/**
	 * 安装包
	 */
    public static boolean installPackage(String host, int pingCount, StringBuffer stringBuffer)
    {
		//apk文件的本地路径
		File apkfile = new File(apkFilePath);
		//会根据用户的数据类型打开android系统相应的Activity。
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//设置intent的数据类型是应用程序application
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		//为这个新apk开启一个新的activity栈
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//开始安装
		startActivity(intent);
		//关闭旧版本的应用程序的进程
		android.os.Process.killProcess(android.os.Process.myPid());
    }
}
