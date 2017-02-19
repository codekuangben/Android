package SDK.Lib.FrameHandle;

import SDK.Lib.DataStruct.MList;
import SDK.Lib.DelayHandle.DelayHandleMgrBase;
import SDK.Lib.DelayHandle.IDelayHandleItem;
import SDK.Lib.Tools.UtilApi;

public class ResizeMgr extends DelayHandleMgrBase implements ITickedObject, IDelayHandleItem
{
    protected int mPreWidth;       // 之前宽度
    protected int mPreHeight;
    protected int mCurWidth;       // 现在宽度
    protected int mCurHeight;

    protected int mCurHalfWidth;       // 当前一半宽度
    protected int mCurHalfHeight;

    protected MList<IResizeObject> mResizeList;

    public ResizeMgr()
    {
        this.mResizeList = new MList<IResizeObject>();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void dispose()
    {
        this.mResizeList.Clear();
    }

    public int getWidth()
    {
        return this.mCurWidth;
    }

    public int getHeight()
    {
        return this.mCurHeight;
    }

    public int getHalfWidth()
    {
        return this.mCurHalfWidth;
    }

    public int getHalfHeight()
    {
        return this.mCurHalfHeight;
    }

    @Override
    protected void addObject(IDelayHandleItem delayObject)
    {
        this.addObject(delayObject, 0);
    }

    @Override
    protected void addObject(IDelayHandleItem delayObject, float priority)
    {
        if(this.mLoopDepth.isInDepth())
        {
            super.addObject(delayObject, priority);
        }
        else
        {
            this.addResizeObject((IResizeObject)delayObject, priority);
        }
    }

    @Override
    protected void removeObject(IDelayHandleItem delayObject)
    {
        if(this.mLoopDepth.isInDepth())
        {
            super.removeObject(delayObject);
        }
        else
        {
            this.removeResizeObject((IResizeObject)delayObject);
        }
    }

    public void addResizeObject(IResizeObject obj)
    {
        this.addResizeObject(obj, 0);
    }

    public void addResizeObject(IResizeObject obj, float priority)
    {
        if (!this.mResizeList.Contains(obj))
        {
            this.mResizeList.Add(obj);
        }
    }

    public void removeResizeObject(IResizeObject obj)
    {
        if (this.mResizeList.IndexOf(obj) != -1)
        {
            this.mResizeList.Remove(obj);
        }
    }

    public void onTick(float delta)
    {
        this.mPreWidth = this.mCurWidth;
        this.mCurWidth = UtilApi.getScreenWidth();
        this.mCurHalfWidth = this.mCurWidth / 2;

        this.mPreHeight = this.mCurHeight;
        this.mCurHeight = UtilApi.getScreenHeight();
        this.mCurHalfHeight = this.mCurHeight / 2;

        if (this.mPreWidth != this.mCurWidth || this.mPreHeight != this.mCurHeight)
        {
            this.onResize(this.mCurWidth, this.mCurHeight);
        }
    }

    public void onResize(int viewWidth, int viewHeight)
    {
        for(IResizeObject resizeObj : mResizeList.list())
        {
            resizeObj.onResize(viewWidth, viewHeight);
        }
    }

    public void setClientDispose(boolean isDispose)
    {

    }

    public boolean isClientDispose()
    {
        return false;
    }
}