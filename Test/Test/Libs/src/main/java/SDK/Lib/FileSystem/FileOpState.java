package SDK.Lib.FileSystem;

public enum FileOpState
{
    eNoOp,      // 无操作
    eOpening,   // 打开中
    eOpenSuccess,   // 打开成功
    eOpenFail,      // 打开失败
    eOpenClose,     // 关闭
}