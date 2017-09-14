package SDK.Lib.Resource.ResLoadData;

import SDK.Lib.FileSystem.MFileSys;

public class ResPathResolve
{
    // MDataStream 加载 File 系统时候的目录
    static public String[] msDataStreamLoadRootPathList;
    // AssetBundles::CreateFromFile 加载时候的目录
    static public String[] msABLoadRootPathList;

    static public void initRootPath()
    {
        // 初始化 WWW 加载目录
        ResPathResolve.msDataStreamLoadRootPathList = new String[ResLoadType.eLoadTotal.ordinal()];

        ResPathResolve.msDataStreamLoadRootPathList[ResLoadType.eLoadResource.ordinal()] = MFileSys.msDataStreamResourcesPath;
        ResPathResolve.msDataStreamLoadRootPathList[ResLoadType.eLoadStreamingAssets.ordinal()] = MFileSys.msDataStreamStreamingAssetsPath;
        ResPathResolve.msDataStreamLoadRootPathList[ResLoadType.eLoadLocalPersistentData.ordinal()] = MFileSys.msDataStreamPersistentDataPath;

        //ResPathResolve.msDataStreamLoadRootPathList[(int)ResLoadType.eLoadWeb] = "http://127.0.0.1/GameWebServer/" + PlatformDefine.PlatformFolder;

        //ResPathResolve.msDataStreamLoadRootPathList[(int)ResLoadType.eLoadWeb] = string.Format("{0}{1}", Ctx.mInstance.mConfig.getAssetServerURL(), PlatformDefine.PlatformFolder);

        // 初始化 AssetBundles 加载目录
        ResPathResolve.msABLoadRootPathList = new String[ResLoadType.eLoadTotal.ordinal()];

        ResPathResolve.msABLoadRootPathList[ResLoadType.eLoadResource.ordinal()] = "";
        ResPathResolve.msABLoadRootPathList[ResLoadType.eLoadStreamingAssets.ordinal()] = MFileSys.msAssetBundlesStreamingAssetsPath;
        ResPathResolve.msABLoadRootPathList[ResLoadType.eLoadLocalPersistentData.ordinal()] = MFileSys.msAssetBundlesPersistentDataPath;

        //ResPathResolve.msABLoadRootPathList[(int)ResLoadType.eLoadWeb] = "http://127.0.0.1/GameWebServer/" + PlatformDefine.PlatformFolder;

        //ResPathResolve.msABLoadRootPathList[(int)ResLoadType.eLoadWeb] = string.Format("{0}{1}", Ctx.mInstance.mConfig.getAssetServerURL(), PlatformDefine.PlatformFolder);
    }
}