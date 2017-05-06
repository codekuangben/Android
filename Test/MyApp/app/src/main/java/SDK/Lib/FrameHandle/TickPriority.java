package SDK.Lib.FrameHandle;

/**
 * @brief Tick 的优先级
 * @brief TP TickPriority 缩写
 */
public class TickPriority
{
    public static float eTPResizeMgr = 100000;   // 窗口大小改变
    public static float eTPDelayTaskMgr = 1f;   // 延迟任务
    public static float eTPLoadProgressMgr = 1f;   // 更新加载进度
}