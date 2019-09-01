package SDK.Lib.Resource.Download;

import SDK.Lib.DataStruct.MDictionary;
import SDK.Lib.DataStruct.MList;

/**
 * @brief 下载数据
 */
public class DownloadData
{
    // 因为资源有些需要协同程序，因此重复利用资源
    public MDictionary<String, DownloadItem> mPath2LoadItem;       // 正在下载的内容 DownloadItem
    public MList<DownloadItem> mWillLoadItem;                           // 将要下载的 DownloadItem
    public MList<DownloadItem> mNoUsedLoadItem;                         // 没有被使用的 DownloadItem

    public DownloadData()
    {
        this.mPath2LoadItem = new MDictionary<String, DownloadItem>();

        this.mWillLoadItem = new MList<DownloadItem>();
        this.mWillLoadItem.setIsSpeedUpFind(true);
        this.mWillLoadItem.setIsOpKeepSort(true);

        this.mNoUsedLoadItem = new MList<DownloadItem>();
        this.mNoUsedLoadItem.setIsSpeedUpFind(true);
        this.mNoUsedLoadItem.setIsOpKeepSort(true);
    }
}