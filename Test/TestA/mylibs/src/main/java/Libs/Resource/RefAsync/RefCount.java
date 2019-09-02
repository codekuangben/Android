package Libs.Resource.RefAsync;

/**
 * @brief 脚本引用计数
 */
public class RefCount
{
    protected int mRefNum;                // 引用计数

    public RefCount()
    {
        this.mRefNum = 0;       // 引用计数从 1 改成 0
    }

    public void init()
    {

    }

    public void dispose()
    {

    }

    public int getRefNum()
    {
        return this.mRefNum;
    }

    public void setRefNum(int value)
    {
        this.mRefNum = value;
    }

    public void reset()
    {
        this.mRefNum = 0;
    }

    public void incRef()
    {
        ++this.mRefNum;
    }

    public void decRef()
    {
        --this.mRefNum;
    }

    public boolean isNoRef()
    {
        return this.mRefNum == 0;
    }

    public void copyFrom(RefCount rhv)
    {
        this.mRefNum = rhv.getRefNum();
    }
}