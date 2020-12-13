package com.kb.mylibs.Tools;

import com.kb.mylibs.EventHandle.ICalleeObject;
import com.kb.mylibs.EventHandle.IDispatchObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UtilPath
{
    // 文件是否存在
    public static boolean existFile(String absolutionFilePath)
    {
        boolean ret = false;
        File file = new File(absolutionFilePath);
        ret = file.exists() && file.isFile();
        return ret;
    }

    // 删除文件
    public static boolean removeFile(String absolutionFilePath)
    {
        boolean ret = true;
        File file = new File(absolutionFilePath);

        if(null != file && file.exists() && file.isFile())
        {
            try
            {
                ret = file.delete();
            }
            catch (Exception error)
            {
                ret = false;
                UtilLog.logFormat(LogTypeId.ePath, "removeFile::removeFile error, path is %s, error is %s", absolutionFilePath, error.getMessage());
            }
        }

        return ret;
    }

    public static void removeFile(File file)
    {
        if(null != file && file.exists() && file.isFile())
        {
            try
            {
                file.delete();
            }
            catch (Exception error)
            {
                UtilLog.logFormat(LogTypeId.ePath, "removeFile::removeFile error, path is %s, error is %s", file.getAbsoluteFile(), error.getMessage());
            }
        }
    }

    // 创建目录
    public static boolean createDirectory(String absolutionDirPath, boolean isRecursion)
    {
        boolean ret = true;
        File dir = new File(absolutionDirPath);

        if (dir.exists())
        {
            ret = dir.mkdirs(); // 递归创建目录
        }

        return ret;
    }

    // 删除目录
    public static void removeDirectory(String absolutionDirPath, boolean isRecursion)
    {
        File dirFile = new File(absolutionDirPath);
        UtilPath.removeDirectory(dirFile);
    }

    public static void removeDirectory(File dirFile)
    {
        if (dirFile != null && dirFile.exists() && dirFile.isDirectory())
        {
            File[] fileArray = dirFile.listFiles();

            for (File file: fileArray)
            {
                if (file.isDirectory())
                {
                    UtilPath.removeDirectory(file);
                }
                else
                {
                    UtilPath.removeFile(file);
                }
            }

            dirFile.delete();
        }
    }

    // 目录是否存在
    public static boolean existDirectory(String absolutionDirPath)
    {
        boolean ret = false;
        File file = new File(absolutionDirPath);
        ret = file.exists() && file.isDirectory();
        return ret;
    }

    public static void traverseDirectory(
            String absolutionDirPath,
            boolean isRecursion,
            ICalleeObject fileEventListener,
            IDispatchObject fileEventHandle,
            ICalleeObject dirEventListener,
            IDispatchObject dirEventHandle)
    {
        File dirFile = new File(absolutionDirPath);

        UtilPath.traverseDirectory(
                dirFile,
                isRecursion,
                fileEventListener,
                fileEventHandle,
                dirEventListener,
                dirEventHandle);
    }

    public static void traverseDirectory(
            File dirFile,
            boolean isRecursion,
            ICalleeObject fileEventListener,
            IDispatchObject fileEventHandle,
            ICalleeObject dirEventListener,
            IDispatchObject dirEventHandle)
    {
        if (dirFile != null && dirFile.exists() && dirFile.isDirectory())
        {
            File[] fileArray = dirFile.listFiles();

            for (File fileOrDir: fileArray)
            {
                if (fileOrDir.isDirectory())
                {


                    if (isRecursion)
                    {
                        UtilPath.traverseDirectory(
                                fileOrDir,
                                isRecursion,
                                fileEventListener,
                                fileEventHandle,
                                dirEventListener,
                                dirEventHandle);
                    }
                }
                else
                {
                    UtilPath.removeFile(fileOrDir);
                }
            }
        }
    }
}