package SDK.Lib.Resource.ResLoadData;

public enum ResLoadStateCV
{
    // 资源加载状态
    eNotLoad,       // 没有加载
    eLoading,       // 正在加载
    eLoaded,        // 加载成功
    eFailed,        // 加载失败

    // 实例化状态
    eNotIns,        // 没有实例化
    eInsing,        // 正在实例化
    eInsSuccess,        // 实例化完成
    eInsFailed,     // 实例化失败
}