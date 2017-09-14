package SDK.Lib.Tools;

import SDK.Lib.Resource.ResLoadData.ResLoadType;
import SDK.Lib.Resource.ResLoadData.ResPathResolve;

public class UtilLogic
{
    public static String combineVerPath(String path, String ver)
    {
        return String.format("%s_v=%s", path, ver);
    }

    public static String webFullPath(String path)
    {
        return String.format("%s%s", ResPathResolve.msDataStreamLoadRootPathList[ResLoadType.eLoadWeb.ordinal()], path);
    }

    public static String getRelPath(String path)
    {
        if (path.indexOf(ResPathResolve.msDataStreamLoadRootPathList[ResLoadType.eLoadWeb.ordinal()]) != -1)
        {
            return path.substring(ResPathResolve.msDataStreamLoadRootPathList[ResLoadType.eLoadWeb.ordinal()].length());
        }

        return path;
    }

    public static String getPathNoVer(String path)
    {
        if (path.indexOf('?') != -1)
        {
            return path.substring(0, path.indexOf('?'));
        }

        return path;
    }
}