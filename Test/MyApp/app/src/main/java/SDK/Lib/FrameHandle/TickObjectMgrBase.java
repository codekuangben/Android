package SDK.Lib.FrameHandle;

import SDK.Lib.DataStruct.MList;
import SDK.Lib.DelayHandle.DelayHandleMgrBase;
import SDK.Lib.DelayHandle.IDelayHandleItem;

// 每一帧执行的对象管理器
public class TickObjectMgrBase extends DelayHandleMgrBase implements ITickedObject, IDelayHandleItem
{
    protected MList<ITickedObject> mTickObjectList;

    public TickObjectMgrBase()
    {
        this.mTickObjectList = new MList<ITickedObject>();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void dispose()
    {

    }

    public void setClientDispose(boolean isDispose)
    {

    }

    public boolean isClientDispose()
    {
        return false;
    }

    public void onTick(float delta)
    {
        this.mLoopDepth.incDepth();

        this.onExecTick(delta);

        this.mLoopDepth.decDepth();
    }

    protected void onExecTick(float delta)
    {
        int idx = 0;
        int count = this.mTickObjectList.Count();
        ITickedObject tickObject = null;

        while (idx < count)
        {
            tickObject = this.mTickObjectList.get(idx);

            if (!((IDelayHandleItem)tickObject).isClientDispose())
            {
                tickObject.onTick(delta);
            }

            ++idx;
        }
    }

    @Override
    protected void addObject(IDelayHandleItem tickObject)
    {
        this.addObject(tickObject, 0);
    }

    @Override
    protected void addObject(IDelayHandleItem tickObject, float priority)
    {
        if (this.mLoopDepth.isInDepth())
        {
            super.addObject(tickObject);
        }
        else
        {
            if (this.mTickObjectList.IndexOf((ITickedObject)tickObject) == -1)
            {
                this.mTickObjectList.Add((ITickedObject)tickObject);
            }
        }
    }

    @Override
    protected void removeObject(IDelayHandleItem tickObject)
    {
        if (this.mLoopDepth.isInDepth())
        {
            super.removeObject(tickObject);
        }
        else
        {
            if (this.mTickObjectList.IndexOf((ITickedObject)tickObject) != -1)
            {
                this.mTickObjectList.Remove((ITickedObject)tickObject);
            }
        }
    }
}