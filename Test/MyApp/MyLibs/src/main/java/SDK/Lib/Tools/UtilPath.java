package SDK.Lib.Tools;

import android.os.Debug;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import SDK.Lib.DataStruct.MList;
import SDK.Lib.FrameWork.MacroDef;
import SDK.Lib.Log.LoggerTool;

public class UtilPath
{
    public static String DOT = ".";
    public static String SLASH = "/";

    public static String SDCARD_ROOT_PATH = "/sdcard";      // sdcard 根目录

    public static String normalPath(String path)
    {
        return path.replace('\\', '/');
    }

    // 删除目录的时候，一定要关闭这个文件夹，否则删除文件夹可能出错, 删除出目录立刻判断目录，结果目录还是存在的
    static public boolean deleteDirectory(String path)
    {
        if (UtilPath.existDirectory(path))
        {
            try
            {
                boolean flag = false;
                // 如果 path不以文件分隔符结尾，自动添加文件分隔符
                if (!path.endsWith(File.separator))
                {
                    path = path + File.separator;
                }

                File dirFile = new File(path);

                if (!dirFile.exists() || !dirFile.isDirectory())
                {
                    return false;
                }

                flag = true;
                File[] files = dirFile.listFiles();

                //遍历删除文件夹下的所有文件(包括子目录)
                for (int i = 0; i < files.length; i++)
                {
                    if (files[i].isFile())
                    {
                        //删除子文件
                        flag = deleteFile(files[i].getAbsolutePath());
                        if (!flag) break;
                    }
                    else
                    {
                        //删除子目录
                        flag = deleteDirectory(files[i].getAbsolutePath());
                        if (!flag)
                        {
                            break;
                        }
                    }
                }

                if (!flag)
                {
                    return false;
                }

                //删除当前空目录
                return dirFile.delete();
            }
            catch (Exception err)
            {
                System.out.print(String.format("UtilPath::DeleteDirectory, error, Error = %s, path = %s", err.getMessage(), path));
            }
        }

        return true;
    }

    // 目录是否存在, 删除出目录立刻判断目录，结果目录还是存在的
    static public boolean existDirectory(String path)
    {
        boolean isExist = false;

        File file = new File(path);
        isExist = file.exists();

        return isExist;
    }

    // 文件是否存在
    static public boolean existFile(String path)
    {
        boolean isExist = false;

        File fileHandle = new File(path);
        isExist = fileHandle.exists() && fileHandle.isFile();

        return isExist;
    }

    // 移动文件
    static public void move(String srcPath, String destPath)
    {
        UtilPath.copyFile(srcPath, destPath, true);
    }

    public static boolean deleteFile(String path)
    {
        boolean isExist = false;

        File fileHandle = new File(path);

        if (fileHandle.isFile() && fileHandle.exists())
        {
            isExist = true;
            try
            {
                fileHandle.delete();
            }
            catch (Exception err)
            {
                System.out.print(String.format("UtilPath::deleteFile, error, Path = $s, ErrorMessage = %s", path, err.getMessage()));
            }
        }

        return isExist;
    }

    // destFileName 目标路径和文件名字
    public static void copyFile(String sourceFileName, String destFileName, boolean isOverWrite)
    {
        try
        {
            int bytesum = 0;
            int byteread = 0;
            File srcfile = new File(sourceFileName);

            if (!srcfile.exists())
            {
                //文件不存在时
                InputStream inStream = new FileInputStream(sourceFileName); //读入原文件
                FileOutputStream fs = new FileOutputStream(destFileName);

                byte[] buffer = new byte[1444];
                int length = 0;

                while((byteread = inStream.read(buffer)) != -1)
                {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }

                inStream.close();
            }
        }
        catch (Exception err)
        {
            System.out.print(String.format("UtilPath::copyFile, error, ErrorMsg = %s, sourceFileName = %s, destFileName = %s", err.getMessage(), sourceFileName, destFileName));
            err.printStackTrace();
        }
    }

    static public void createDirectory(String pathAndName, boolean isRecurse)
    {
        if (isRecurse)
        {
            String normPath = normalPath(pathAndName);
            String[] pathArr = normPath.split("/");

            String curCreatePath = "";
            int idx = 0;

            for (; idx < pathArr.length; ++idx)
            {
                // Mac 下是以 ‘／’ 开头的，如果使用  '/' 分割字符串，就会出现字符长度为 0 的问题
                if (0 != pathArr[idx].length())
                {
                    if(curCreatePath.length() == 0)
                    {
                        curCreatePath = pathArr[idx];
                    }
                    else
                    {
                        curCreatePath = String.format("%s/%s", curCreatePath, pathArr[idx]);
                    }

                    if (!UtilPath.existDirectory(curCreatePath))
                    {
                        try
                        {
                            File dirFolder = new File(curCreatePath);

                            if(!dirFolder.exists())
                            {
                                dirFolder.mkdirs();     // dirFolder.mkdir(); 只创建当前目录， dirFolder.mkdirs() 创建所有父目录及当前目录
                            }
                        }
                        catch(Exception err)
                        {
                            System.out.print(String.format("UitlPath::CreateDirectory, error, ErrorMsg = %s, path = %s", err.getMessage(), curCreatePath));
                        }
                    }
                }
                else
                {
                    if(0 == idx && pathAndName.charAt(idx) == '/')
                    {
                        curCreatePath = "/";
                    }
                }
            }
        }
        else
        {
            try
            {
                if (!UtilPath.existDirectory(pathAndName))
                {
                    // 这个接口默认就支持创建所有没有的目录
                    UtilPath.createDirectory(pathAndName, true);
                }
            }
            catch (Exception err)
            {
                System.out.print(String.format("UtilPath::CreateDirectory, error, ErrorMsg = %s, pathAndName = %s", err.getMessage(), pathAndName));
            }
        }
    }

    static public boolean renameFile(String srcPath, String destPath)
    {
        try
        {
            if (UtilPath.existFile(srcPath))
            {
                UtilPath.move(srcPath, destPath);
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception excep)
        {
            System.out.print(String.format("UtilPath::renameFile, error, ErrorMsg = %s, srcPath = %s, destPath = %s", excep.getMessage(), srcPath, destPath));
            return false;
        }
    }

    static public String combine(String path_a, String path_b)
    {
        String[] pathList = new String[2];
        pathList[0] = path_a;
        pathList[1] = path_b;

        return UtilPath.combine(pathList);
    }

    static public String combine(String path_a, String path_b, String path_c)
    {
        String[] pathList = new String[3];
        pathList[0] = path_a;
        pathList[1] = path_b;
        pathList[2] = path_c;

        return UtilPath.combine(pathList);
    }

    static public String combine(String path_a, String path_b, String path_c, String path_d)
    {
        String[] pathList = new String[4];
        pathList[0] = path_a;
        pathList[1] = path_b;
        pathList[2] = path_c;
        pathList[3] = path_d;

        return UtilPath.combine(pathList);
    }

    static public String combine(String path_a, String path_b, String path_c, String path_d, String path_e)
    {
        String[] pathList = new String[5];
        pathList[0] = path_a;
        pathList[1] = path_b;
        pathList[2] = path_c;
        pathList[3] = path_d;
        pathList[4] = path_e;

        return UtilPath.combine(pathList);
    }

    static public String combine(String path_a, String path_b, String path_c, String path_d, String path_e, String path_f)
    {
        String[] pathList = new String[6];
        pathList[0] = path_a;
        pathList[1] = path_b;
        pathList[2] = path_c;
        pathList[3] = path_d;
        pathList[4] = path_e;
        pathList[5] = path_f;

        return UtilPath.combine(pathList);
    }

    static public String combine(String[] pathList)
    {
        int idx = 0;
        String ret = "";
        boolean isFirst = true;
        StringBuilder stringBuilder = new StringBuilder();

        while (idx < pathList.length)
        {
            if (pathList[idx].length() > 0)
            {
                //if(stringBuilder.ToString(stringBuilder.Length - 1, 1) != "/" || pathList[idx][pathList[idx].Length - 1] != '/')
                //{
                //    stringBuilder.Append("/");
                //}

                if(!isFirst)
                {
                    stringBuilder.append("/");
                }
                else
                {
                    isFirst = false;
                }

                stringBuilder.append(pathList[idx]);
            }

            idx += 1;
        }

        ret = stringBuilder.toString();
        // 替换掉空目录，但是 Android 下目录是 msStreamingAssetsPath 目录是 jar:file:///data/app/ ，替换会修改目录
        //ret = ret.Replace("//", "/");

        return ret;
    }

    // 获取扩展名
    static public String getFileExt(String path)
    {
        String extName = "";

        int dotIdx = path.lastIndexOf('.');

        if (-1 != dotIdx)
        {
            extName = path.substring(dotIdx + 1);
        }

        return extName;
    }

    // 获取文件名字，没有路径，但是有扩展名字
    static public String getFileNameWithExt(String fullPath)
    {
        int index = fullPath.lastIndexOf('/');
        String ret = "";

        if (index == -1)
        {
            index = fullPath.lastIndexOf('\\');
        }
        if (index != -1)
        {
            ret = fullPath.substring(index + 1);
        }
        else
        {
            ret = fullPath;
        }

        return ret;
    }

    // 获取文件名字，没有扩展名字
    static public String getFileNameNoExt(String fullPath)
    {
        int index = fullPath.lastIndexOf('/');
        String ret = "";

        if (index == -1)
        {
            index = fullPath.lastIndexOf('\\');
        }
        if (index != -1)
        {
            ret = fullPath.substring(index + 1);
        }
        else
        {
            ret = fullPath;
        }

        index = ret.lastIndexOf('.');
        if (index != -1)
        {
            ret = ret.substring(0, index);
        }

        return ret;
    }

    // 获取文件路径，没有文件名字
    static public String getFilePathNoName(String fullPath)
    {
        int index = fullPath.lastIndexOf('/');
        String ret = "";

        if (index == -1)
        {
            index = fullPath.lastIndexOf('\\');
        }
        if (index != -1)
        {
            ret = fullPath.substring(0, index);
        }
        else
        {
            ret = fullPath;
        }

        return ret;
    }

    // 获取文件路径，没有文件名字扩展
    static public String getFilePathNoExt(String fullPath)
    {
        int index = 0;
        String ret = fullPath;
        index = fullPath.lastIndexOf('.');

        if (index != -1)
        {
            ret = fullPath.substring(0, index);
        }

        return ret;
    }

    // 获取当前文件的父目录名字
    static public String getFileParentDirName(String fullPath)
    {
        String parentDir = "";
        int lastSlashIndex = -1;

        // 如果是文件
        if (UtilPath.existFile(fullPath))
        {
            lastSlashIndex = fullPath.lastIndexOf("/");

            if(-1 == lastSlashIndex)
            {
                lastSlashIndex = fullPath.lastIndexOf("\\");
            }

            if (-1 == lastSlashIndex)
            {
                parentDir = "";
            }
            else
            {
                fullPath = fullPath.substring(0, lastSlashIndex);

                lastSlashIndex = fullPath.lastIndexOf("/");

                if (-1 == lastSlashIndex)
                {
                    lastSlashIndex = fullPath.lastIndexOf("\\");
                }

                if (-1 == lastSlashIndex)
                {
                    parentDir = fullPath;
                }
                else
                {
                    parentDir = fullPath.substring(lastSlashIndex + 1, fullPath.length() - (lastSlashIndex + 1));
                }
            }
        }
        else
        {
            lastSlashIndex = fullPath.lastIndexOf("/");

            if (-1 == lastSlashIndex)
            {
                lastSlashIndex = fullPath.lastIndexOf("\\");
            }

            if (-1 == lastSlashIndex)
            {
                parentDir = "";
            }
            else
            {
                parentDir = fullPath.substring(lastSlashIndex + 1, fullPath.length() - (lastSlashIndex + 1));
            }
        }

        return parentDir;
    }

    // 搜索文件夹中的文件
    static public MList<String> getAllFile(String path, MList<String> includeExtList, MList<String> excludeExtList, boolean recursion)
    {
        File dir = new File(path);
        MList<String> fileList = new MList<String>();

        String extName = "";
        File[] allFile = dir.listFiles();

        for(File file : allFile)
        {
            if(file.isFile())
            {
                extName = UtilPath.getFileExt(file.getAbsolutePath());
                if (includeExtList != null && includeExtList.IndexOf(extName) != -1)
                {
                    fileList.Add(normalPath(file.getAbsolutePath()));
                }
                else if (excludeExtList != null && excludeExtList.IndexOf(extName) == -1)
                {
                    fileList.Add(normalPath(file.getAbsolutePath()));
                }
                else if (includeExtList == null && excludeExtList == null)
                {
                    fileList.Add(normalPath(file.getAbsolutePath()));
                }
            }
        }

        if (recursion)
        {
            for(File file : allFile)
            {
                if(file.isDirectory())
                {
                    fileList.merge(getAllFile(file.getAbsolutePath(), includeExtList, excludeExtList, recursion));
                }
            }
        }
        return fileList;
    }

    // 添加版本的文件名，例如 E:/aaa/bbb/ccc.txt?v=1024
    public static String versionPath(String path, String version)
    {
        if (!UtilStr.isNullOrEmpty(version))
        {
            return String.format("%s?v=%s", path, version);
        }
        else
        {
            return path;
        }
    }

    // 删除所有除去版本号外相同的文件，例如 E:/aaa/bbb/ccc.txt?v=1024 ，只要 E:/aaa/bbb/ccc.txt 一样就删除，参数就是 E:/aaa/bbb/ccc.txt ，没有版本号的文件
    public static void delFileNoVer(String path)
    {
        path = normalPath(path);

        File TheFolder = new File(path.substring(0, path.lastIndexOf('/')));
        File[] allFiles = TheFolder.listFiles();

        for(File item : allFiles)
        {
            if(item.getAbsolutePath().matches(String.format("{0}*", path)))
            {
                item.delete();
            }
        }
    }

    public static boolean fileExistNoVer(String path)
    {
        boolean ret = false;

        path = normalPath(path);

        File TheFolder = new File(path.substring(0, path.lastIndexOf('/')));
        File[] allFiles = TheFolder.listFiles();

        for(File item : allFiles)
        {
            if(item.getAbsolutePath().matches(String.format("{0}*", path)))
            {
                ret = true;
                break;
            }
        }

        return ret;

    }

    static public void saveStr2File(String str, String filePath, MEncoding encoding)
    {
        try
        {
            byte[] bytes = encoding.GetBytes(str);

            FileOutputStream outputStream = new FileOutputStream(filePath);

            outputStream.write(bytes);
        }
        catch(Exception err)
        {

        }
    }

    static public void saveByte2File(String path, byte[] bytes)
    {
        try
        {
            FileOutputStream outputStream = new FileOutputStream(path);

            outputStream.write(bytes);
        }
        catch(Exception err)
        {

        }
    }

    // 递归拷贝目录
    static public void copyDirectory(String srcPath, String destPath, boolean isRecurse)
    {
        File sourceDirInfo = new File(srcPath);
        File targetDirInfo = new File(destPath);

        if (targetDirInfo.getAbsolutePath().startsWith(sourceDirInfo.getAbsolutePath()))
        {
            System.out.print("UtilPath::copyDirectory, error, destPath is srcPath subDir, can not copy");
            return;
        }

        if (!sourceDirInfo.exists())
        {
            return;
        }

        if (!targetDirInfo.exists())
        {
            targetDirInfo.mkdirs();
        }

        File[] files = sourceDirInfo.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            if(files[i].isFile())
            {
                UtilPath.copyFile(files[i].getAbsolutePath(), targetDirInfo.getAbsolutePath() + "/" + files[i].getName(), true);
            }
        }

        if (isRecurse)
        {
            for (int j = 0; j < files.length; j++)
            {
                if(files[j].isDirectory())
                {
                    copyDirectory(files[j].getAbsolutePath(), targetDirInfo.getAbsolutePath() + "/" + files[j].getName(), isRecurse);
                }
            }
        }
    }

    static public void traverseDirectory(
        String srcPath,
        String destPath,
        ITraverseDirectoryCalleeObject dirHandle /*= null*/,
        ITraverseDirectoryCalleeObject fileHandle /*= null*/,
        boolean isRecurse /*= false*/,
        boolean isCreateDestPath /*= false*/
        )
    {
        File sourceDirInfo = new File(srcPath);
        File targetDirInfo = null;

        // 如果不是目录规则的字符串，执行 new DirectoryInfo(destPath); 会报错
        if (!UtilStr.isNullOrEmpty(destPath))
        {
            targetDirInfo = new File(destPath);

            if (targetDirInfo.getAbsolutePath().startsWith(sourceDirInfo.getAbsolutePath()))
            {
                System.out.print(String.format("UtilPath::traverseDirectory, error, destPath is srcPath subDir, can not copy"));
                return;
            }
        }

        if (!sourceDirInfo.exists())
        {
            return;
        }

        if (!UtilStr.isNullOrEmpty(destPath))
        {
            if (!UtilPath.existDirectory(destPath) && isCreateDestPath)
            {
                UtilPath.createDirectory(destPath, true);
                targetDirInfo = new File(destPath);
            }
        }

        if (dirHandle != null)
        {
            if (UtilStr.isNullOrEmpty(destPath))
            {
                dirHandle.call(sourceDirInfo.getAbsolutePath(), sourceDirInfo.getName(), "");
            }
            else
            {
                dirHandle.call(sourceDirInfo.getAbsolutePath(), sourceDirInfo.getName(), targetDirInfo.getAbsolutePath());
            }
        }

        File[] files = sourceDirInfo.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            if(files[i].isFile())
            {
                if (fileHandle != null)
                {
                    if (UtilStr.isNullOrEmpty(destPath))
                    {
                        fileHandle.call(files[i].getAbsolutePath(), files[i].getName(), "");
                    }
                    else
                    {
                        fileHandle.call(files[i].getAbsolutePath(), files[i].getName(), targetDirInfo.getAbsolutePath());
                    }
                }
            }
        }

        if (isRecurse)
        {
            for(int j = 0; j < files.length; j++)
            {
                if(files[j].isDirectory())
                {
                    if (UtilStr.isNullOrEmpty(destPath))
                    {
                        traverseDirectory(files[j].getAbsolutePath(), "", dirHandle, fileHandle, isRecurse, isCreateDestPath);
                    }
                    else
                    {
                        traverseDirectory(files[j].getAbsolutePath(), targetDirInfo.getAbsolutePath() + "/" + files[j].getName(), dirHandle, fileHandle, isRecurse, isCreateDestPath);
                    }
                }
            }
        }
    }

    static public void deleteFiles(String srcPath, MList<String> fileList, MList<String> extNameList, boolean isRecurse)
    {
        File fatherFolder = new File(srcPath);
        //删除当前文件夹内文件
        File[] files = fatherFolder.listFiles();
        String extName = "";

        for(File file : files)
        {
            if(file.isFile())
            {
                String fileName = file.getName();

                if (fileList != null)
                {
                    if (fileList.IndexOf(fileName) != -1)
                    {
                        UtilPath.deleteFile(file.getAbsolutePath());
                    }
                }
                if (extNameList != null)
                {
                    extName = UtilPath.getFileExt(file.getAbsolutePath());
                    if (extNameList.IndexOf(extName) != -1)
                    {
                        UtilPath.deleteFile(file.getAbsolutePath());
                    }
                }
            }
        }
        if (isRecurse)
        {
            //递归删除子文件夹内文件
            for(File file : files)
            {
                if(file.isDirectory())
                {
                    UtilPath.deleteFiles(file.getAbsolutePath(), fileList, extNameList, isRecurse);
                }
            }
        }
    }

    // 递归删除所有的文件和目录
    static public void deleteSubDirsAndFiles(String curDir, MList<String> excludeDirList, MList<String> excludeFileList)
    {
        File fatherFolder = new File(curDir);
        //删除当前文件夹内文件
        File[] files = fatherFolder.listFiles();
        String normalPath = "";

        for(File file : files)
        {
            if(file.isFile())
            {
                String fileName = file.getName();
                normalPath = UtilPath.normalPath(file.getAbsolutePath());

                if (!UtilPath.isEqualStrInList(normalPath, excludeFileList))
                {
                    UtilPath.deleteFile(file.getAbsolutePath());
                }
            }
        }

        // 递归删除子文件夹内文件
        for(File childFolder : files)
        {
            if(childFolder.isDirectory())
            {
                normalPath = UtilPath.normalPath(childFolder.getAbsolutePath());

                if(!UtilPath.isEqualStrInList(normalPath, excludeDirList))
                {
                    if (UtilPath.isSubStrInList(normalPath, excludeDirList) && !UtilPath.isSubStrInList(normalPath, excludeFileList))
                    {
                        UtilPath.deleteDirectory(childFolder.getAbsolutePath());
                    }
                    else
                    {
                        UtilPath.deleteSubDirsAndFiles(childFolder.getAbsolutePath(), excludeDirList, excludeFileList);
                    }
                }
            }
        }
    }

    // 字符串是否是子串
    static public boolean isSubStrInList(String str, MList<String> list)
    {
        boolean ret = false;

        int idx = 0;
        int len = 0;

        if(list != null)
        {
            idx = 0;
            len = list.length();

            while(idx < len)
            {
                if(list.get(idx).indexOf(str) != -1)
                {
                    ret = true;
                    break;
                }

                ++idx;
            }
        }

        return ret;
    }

    static public boolean isEqualStrInList(String str, MList<String> list)
    {
        boolean ret = false;

        int idx = 0;
        int len = 0;

        if (list != null)
        {
            idx = 0;
            len = list.length();

            while (idx < len)
            {
                if (list.get(idx) == str)
                {
                    ret = true;
                    break;
                }

                ++idx;
            }
        }

        return ret;
    }

    // 打包成 unity3d 后文件名字会变成小写，这里修改一下
    static public void modifyFileNameToCapital(String path, String fileNameNoExt)
    {
        String srcFullPath = String.format("{0}/{1}.{2}", path, fileNameNoExt.toLowerCase(), UtilApi.UNITY3D);
        String destFullPath = String.format("{0}/{1}.{2}", path, fileNameNoExt, UtilApi.UNITY3D);
        UtilPath.move(srcFullPath, destFullPath);

        srcFullPath = String.format("{0}/{1}.{2}.manifest", path, fileNameNoExt.toLowerCase(), UtilApi.UNITY3D);
        destFullPath = String.format("{0}/{1}.{2}.manifest", path, fileNameNoExt, UtilApi.UNITY3D);
        UtilPath.move(srcFullPath, destFullPath);
    }

    // 大写转换成小写
    static public String toLower(String src)
    {
        return src.toLowerCase();
    }

    // 递归创建子目录
    public static void recureCreateSubDir(String rootPath, String subPath, boolean includeLast)
    {
        subPath = normalPath(subPath);
        if (!includeLast)
        {
            if (subPath.indexOf('/') == -1)
            {
                return;
            }
            subPath = subPath.substring(0, subPath.lastIndexOf('/'));
        }

        if (UtilPath.existDirectory(UtilPath.combine(rootPath, subPath)))
        {
            return;
        }

        int startIdx = 0;
        int splitIdx = 0;
        while ((splitIdx = subPath.indexOf('/', startIdx)) != -1)
        {
            if (!UtilPath.existDirectory(UtilPath.combine(rootPath, subPath.substring(0, startIdx + splitIdx))))
            {
                UtilPath.createDirectory(UtilPath.combine(rootPath, subPath.substring(0, startIdx + splitIdx)), true);
            }

            startIdx += splitIdx;
            startIdx += 1;
        }

        UtilPath.createDirectory(UtilPath.combine(rootPath, subPath), true);
    }

    static public String getCurrentDirectory()
    {
        //String curPath = getApplicationContext().getFilesDir().getAbsolutePath();
        //String curPath = Environment.get.getFilesDir().getAbsolutePath();
        String curPath = "";
        return curPath;
    }

    // 去掉文件扩展名字，文件判断后缀是否是指定后缀
    static public boolean isFileNameSuffixNoExt(String path, String suffix)
    {
        path = UtilPath.normalPath(path);

        boolean ret = false;

        int dotIdx = 0;
        dotIdx = path.lastIndexOf(UtilPath.DOT);

        if (-1 != dotIdx)
        {
            path = path.substring(0, dotIdx);
        }

        int slashIdx = 0;
        slashIdx = path.lastIndexOf(UtilPath.SLASH);

        if (-1 != slashIdx)
        {
            path = path.substring(slashIdx + 1);
        }

        if (path.length() > suffix.length())
        {
            if (path.substring(path.length() - suffix.length(), suffix.length()) == suffix)
            {
                ret = true;
            }
        }

        return ret;
    }

    // 去掉文件扩展名字，然后再去掉文件后缀
    static public String getFileNameRemoveSuffixNoExt(String path, String suffix)
    {
        path = UtilPath.normalPath(path);

        String ret = path;

        int dotIdx = 0;
        dotIdx = path.lastIndexOf(UtilPath.DOT);

        if (-1 != dotIdx)
        {
            path = path.substring(0, dotIdx);
        }

        int slashIdx = 0;
        slashIdx = path.lastIndexOf(UtilPath.SLASH);

        if (-1 != slashIdx)
        {
            path = path.substring(slashIdx + 1);
        }

        if (path.length()> suffix.length())
        {
            if (path.substring(path.length() - suffix.length(), suffix.length()) == suffix)
            {
                ret = path.substring(0, path.length() - suffix.length());
            }
        }

        return ret;
    }

    // 通过文件名字和版本检查文件是否存在
    static public boolean checkFileFullNameExistByVersion(String fileFullName, String version)
    {
        boolean ret = false;

        String path = UtilPath.versionPath(fileFullName, version);
        ret = UtilPath.existFile(path);

        return ret;
    }

    // 删除指定目录下所有类似的文件
    static public void deleteAllSearchPatternFile(String fileFullName)
    {
        File dir = new File(fileFullName);
        File[] fileList = dir.listFiles();

        int index = 0;
        int listLen = fileList.length;

        while(index < listLen)
        {
             UtilPath.deleteFile(fileList[index].getAbsolutePath());

            index += 1;
        }
    }

    // 删除出目录立刻判断目录，结果目录还是存在的
    static public void clearOrCreateDirectory(String path)
    {
        if (!UtilPath.existDirectory(path))
        {
            UtilPath.createDirectory(path, true);
        }
        else
        {
            File dirInfo = new File(path);

            File[] allDir = dirInfo.listFiles();
            for(File dirItem: allDir)
            {
                if(dirItem.isDirectory())
                {
                    if (UtilPath.existDirectory(dirItem.getAbsolutePath()))
                    {
                        UtilPath.deleteDirectory(dirItem.getAbsolutePath());
                    }
                }
            }

            File[] allFile = dirInfo.listFiles();
            for(File file : allFile)
            {
                if(file.isFile())
                {
                    if (UtilPath.existFile(file.getAbsolutePath()))
                    {
                        UtilPath.deleteFile(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    static public boolean isAbsoluteDir(String path)
    {
        boolean ret = false;

        if('/' == path.charAt(0) ||
            -1 != path.indexOf(":/") ||
            -1 != path.indexOf(":\\"))
        {
            ret = true;
        }

        return ret;
    }

    // 去掉绝对目录中的 ../.. 符号
    static public String convAbsoluteDir(String path)
    {
        String ret = "";

        if(UtilPath.isAbsoluteDir(path))
        {
            path = UtilPath.normalPath(path);
            String[] pathArr = path.split("/");
            MList<String> pathStack = new MList<String>();

            if('/' == path.charAt(0))
            {
                pathStack.add("/");
            }

            int index = 0;
            int listLen = pathArr.length;

            while(index < listLen)
            {
                if(".." == pathArr[index])
                {
                    if(pathStack.Count() > 1)
                    {
                        pathStack.removeAt(pathStack.Count() - 1);
                    }
                    else
                    {
                        if(MacroDef.ENABLE_LOG)
                        {
                            System.out.print(String.format("UtilPath::convAbsoluteDir, error, path = {0}", path));
                            break;
                        }
                    }
                }
                else
                {
                    pathStack.add(pathArr[index]);
                }

                index += 1;
            }

            index = 0;
            listLen = pathStack.Count();

            while(index < listLen)
            {
                if("" == ret)
                {
                    ret = pathStack.get(index);
                }
                else
                {
                    ret += "/";
                    ret += pathStack.get(index);
                }

                index += 1;
            }
        }
        else
        {
            ret = path;
        }

        return ret;
    }
}