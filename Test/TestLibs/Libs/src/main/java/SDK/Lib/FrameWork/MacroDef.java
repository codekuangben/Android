package SDK.Lib.FrameWork;

public class MacroDef
{
    // 宏定义开始
    // 调试不需要网络
    static public boolean DEBUG_NOTNET = false;

    // 网络处理多线程，主要是调试的时候使用单线程，方便调试，运行的时候使用多线程
    static public boolean NET_MULTHREAD = true;

    // 是否检查函数接口调用线程
    static public boolean THREAD_CALLCHECK = true;

    // 开启窗口日志
    static public boolean ENABLE_WINLOG = true;

    // 开启网络日志
    static public boolean ENABLE_NETLOG = false;

    // 开启文件日志
    static public boolean ENABLE_FILELOG = false;

    // 单元测试，这个需要宏定义
    static public boolean UNIT_TEST = true;

    // 开启日志
    static public boolean ENABLE_LOG = true;

    // 开启警告
    static public boolean ENABLE_WARN = false;

    // 开启错误
    static public boolean ENABLE_ERROR = true;

    // 压缩
    static public boolean MSG_COMPRESS = true;
    // 宏定义结束
}