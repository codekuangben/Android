package SDK.Lib.Resource.ResLoadData;

public class ResLoadState
{
    protected ResLoadStateCV mResLoadState;

    public ResLoadState()
    {
        this.mResLoadState = ResLoadStateCV.eNotLoad;
    }

    public ResLoadStateCV getResLoadState()
    {
        return this.mResLoadState;
    }

    public void setResLoadState(ResLoadStateCV value)
    {
        this.mResLoadState = value;
    }

    public void reset()
    {
        this.mResLoadState = ResLoadStateCV.eNotLoad;
    }

    // 是否加载完成，可能成功可能失败
    public boolean hasLoaded()
    {
        return this.mResLoadState == ResLoadStateCV.eFailed || mResLoadState == ResLoadStateCV.eLoaded;
    }

    public boolean hasSuccessLoaded()
    {
        return this.mResLoadState == ResLoadStateCV.eLoaded;
    }

    public boolean hasFailed()
    {
        return this.mResLoadState == ResLoadStateCV.eFailed;
    }

    // 没有加载或者正在加载中
    public boolean hasNotLoadOrLoading()
    {
        return (this.mResLoadState == ResLoadStateCV.eLoading || this.mResLoadState == ResLoadStateCV.eNotLoad);
    }

    public void setSuccessLoaded()
    {
        this.mResLoadState = ResLoadStateCV.eLoaded;
    }

    public void setFailed()
    {
        this.mResLoadState = ResLoadStateCV.eFailed;
    }

    public void setLoading()
    {
        this.mResLoadState = ResLoadStateCV.eLoading;
    }

    // 成功实例化
    public void setSuccessIns()
    {
        this.mResLoadState = ResLoadStateCV.eInsSuccess;
    }

    // 实例化失败
    public void setInsFailed()
    {
        this.mResLoadState = ResLoadStateCV.eInsFailed;
    }

    // 正在实例化
    public void setInsing()
    {
        this.mResLoadState = ResLoadStateCV.eInsing;
    }

    // 是否成功实例化
    public boolean hasSuccessIns()
    {
        return (this.mResLoadState == ResLoadStateCV.eInsSuccess);
    }

    // 是否实例化失败
    public boolean hasInsFailed()
    {
        return (this.mResLoadState == ResLoadStateCV.eInsFailed);
    }

    // 是否正在实例化
    public boolean hasInsing()
    {
        return (this.mResLoadState == ResLoadStateCV.eInsing);
    }

    public void copyFrom(ResLoadState rhv)
    {
        this.mResLoadState = rhv.getResLoadState();
    }
}