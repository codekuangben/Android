package Libs.Resource.RefAsync;

/**
 * @brief 引用计数资源加载结果通知
 */
public class RefCountResLoadResultNotify extends ResLoadResultNotify
{
    protected RefCount mRefCount;                  // 引用计数

    public RefCountResLoadResultNotify()
    {
        this.mRefCount = new RefCount();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void dispose()
    {
        if(null != this.mRefCount)
        {
            this.mRefCount.dispose();
            this.mRefCount = null;
        }

        super.dispose();
    }

    public RefCount getRefCount()
    {
        return this.mRefCount;
    }

    public void setRefCount(RefCount value)
    {
        this.mRefCount = value;
    }

    @Override
    public void copyFrom(ResLoadResultNotify rhv)
    {
        super.copyFrom(rhv);

        this.mRefCount.copyFrom(((RefCountResLoadResultNotify)rhv).getRefCount());
    }
}