package SDK.Lib.Tools;

/**
 * @brief 遍历目录回调
 */
public interface ITraverseDirectoryCalleeObject
{
    void call(String srcFileFullPath, String fileName, String destDirFullPath);
}
