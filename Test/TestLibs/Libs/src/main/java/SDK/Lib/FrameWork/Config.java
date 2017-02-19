package SDK.Lib.FrameWork;

/**
 * @brief 宏定义说明区域
 */

import SDK.Lib.DataStruct.MList;

/**
 * @brief 基本的配置
 */
public class Config
{
    public static String StreamingAssets;

    public String mIp;
    public int mPort;
    public short mZone;

    public String mWebIP;               // web 服务器
    public int mWebPort;

    public String[] mPathLst;
    public String mDataPath;
    public String mNetLogPhp;       // Php 处理文件
    public MList<String> mPakExtNameList;       // 打包的扩展名字列表

    public boolean mIsActorMoveUseFixUpdate;    // Actor 移动是否使用固定更新，主要是方便参与物理运算

    public Config()
    {
        StreamingAssets = "StreamingAssets/";

        this.mIp = "192.168.96.14";
        this.mPort = 20013;
        this.mZone = 30;

        this.mWebIP = "http://127.0.0.1/UnityServer/";
        this.mWebPort = 80;
        this.mNetLogPhp = "/netlog/NetLog.php";
        this.mPakExtNameList = new MList<String>();

//        this.mPathLst = new string[(int)ResPathType.eTotal];
//        this.mPathLst[(int)ResPathType.ePathScene] = "Scenes/";
//        this.mPathLst[(int)ResPathType.ePathSceneXml] = "Scenes/Xml/";
//        this.mPathLst[(int)ResPathType.ePathModule] = "Module/";

        this.mPakExtNameList.Add("prefab");
        this.mPakExtNameList.Add("png");
        this.mPakExtNameList.Add("shader");
        this.mPakExtNameList.Add("unity");

        this.mIsActorMoveUseFixUpdate = true;
    }
}