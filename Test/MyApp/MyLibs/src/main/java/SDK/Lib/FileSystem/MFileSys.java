package SDK.Lib.FileSystem;

import SDK.Lib.FrameWork.MacroDef;
import SDK.Lib.Resource.ResLoadData.ResLoadType;

/**
 * @brief 本地文件系统
 */
public class MFileSys
{
    // 使用目录都直接使用这四个目录
    public static String msStreamingAssetsPath;
    public static String msPersistentDataPath;

    public static String msWWWStreamingAssetsPath;  // www 读取 StreamingAssets 文件系统时候不同平台的目录
    public static String msAssetBundlesStreamingAssetsPath;     // AssetBundles CreateFromFile 直接从 StreamingAssets 目录下读取 AssetBundles 资源的目录

    public static String msWWWPersistentDataPath;   // 使用 WWW 读取 PersistentDataPath 的路径
    public static String msAssetBundlesPersistentDataPath;    // AssetBundles CreateFromFile 直接从 StreamingAssets 目录下读取 AssetBundles 资源的目录

    public static String msDataStreamResourcesPath;     // 使用 MDataStream 读取 Resources 目录下的资源
    public static String msDataStreamStreamingAssetsPath;     // 使用 MDataStream 读取 StreamingAssetsPath 目录下的资源
    public static String msDataStreamPersistentDataPath;     // 使用 MDataStream 读取 PersistentDataPath 目录下的资源

    // 可读写目录
    //#if UNITY_EDITOR
    //public static string msRWDataPath = msStreamingAssetsPath;
    //#else
    //public static string msRWDataPath = msPersistentDataPath;
    //#endif

    // 初始化资源目录
    static public void init()
    {
        //MFileSys.msStreamingAssetsPath = Application.streamingAssetsPath;
        //MFileSys.msPersistentDataPath = Application.persistentDataPath;

        MFileSys.msWWWStreamingAssetsPath = MFileSys.getWWWStreamingAssetsPath();
        MFileSys.msAssetBundlesStreamingAssetsPath = MFileSys.getAssetBundlesStreamingAssetsPath();

        MFileSys.msWWWPersistentDataPath = MFileSys.getWWWPersistentDataPath();
        MFileSys.msAssetBundlesPersistentDataPath = MFileSys.getAssetBundlesPersistentDataPath();

        MFileSys.msDataStreamResourcesPath = ":";
        MFileSys.msDataStreamStreamingAssetsPath = MFileSys.getDataStreamStreamingAssetsPath();
        MFileSys.msDataStreamPersistentDataPath = MFileSys.getDataStreamPersistentDataPath();
    }

    // 获取本地 Data 目录
    static public String getLocalDataDir()
    {
        //return Application.dataPath;
        return "";
    }

    // 获取本地可以读取的 StreamingAssets 目录，不同平台下 StreamingAssets 是不同的，但是不能写，这个目录主要使用 WWW 读取资源。这个接口和接口 getWWWStreamingAssetsPath 是相同的功能，只不过是不同的写法，以后都是用 getWWWStreamingAssetsPath 这个接口，不要再使用这个接口了
    static public String getLocalReadDir()
    {
        return "";
    }

    // 获取本地可以写的目录
    static public String getLocalWriteDir()
    {
        // get_persistentDataPath can only be called from the main thread
        //return Application.persistentDataPath;      // 这个目录是可读写的
        return MFileSys.msPersistentDataPath;
    }

    // http://docs.unity3d.com/ScriptReference/WWW.html
    static protected String getWWWStreamingAssetsPath()
    {
        String filepath  = "";
        return filepath;
    }

    static protected String getAssetBundlesStreamingAssetsPath()
    {
        String filepath  = "";
        return filepath;
    }

    static public String getWWWPersistentDataPath()
    {
        return MFileSys.msPersistentDataPath;
    }

    static public String getAssetBundlesPersistentDataPath()
    {
        return MFileSys.msPersistentDataPath;
    }

    static public String getDataStreamStreamingAssetsPath()
    {
        return MFileSys.msStreamingAssetsPath;
    }

    static public String getDataStreamPersistentDataPath()
    {
        return MFileSys.msPersistentDataPath;
    }

    // 获取编辑器工作目录
    static public String getWorkPath()
    {
        //return System.Environment.CurrentDirectory;
        return "";
    }

    // 获取编辑器工作目录
    static public String getDebugWorkPath()
    {
        String path = String.format("{0}{1}", getWorkPath(), "/Debug");
        return path;
    }
}