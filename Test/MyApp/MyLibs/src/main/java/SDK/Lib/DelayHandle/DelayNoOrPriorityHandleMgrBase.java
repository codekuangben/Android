package SDK.Lib.DelayHandle;

import SDK.Lib.Core.GObject;
import SDK.Lib.DataStruct.NoOrPriorityList.INoOrPriorityList;
import SDK.Lib.DataStruct.NoOrPriorityList.INoOrPriorityObject;
import SDK.Lib.EventHandle.ICalleeObject;
import SDK.Lib.EventHandle.IDispatchObject;
import SDK.Lib.FrameHandle.LoopDepth;

/**
 * @brief 当需要管理的对象可能在遍历中间添加的时候，需要这个管理器
 */
public class DelayNoOrPriorityHandleMgrBase extends GObject implements ICalleeObject
{
    protected INoOrPriorityList mDeferredAddQueue;
    protected INoOrPriorityList mDeferredDelQueue;

    protected LoopDepth mLoopDepth;           // 是否在循环中，支持多层嵌套，就是循环中再次调用循环

    public DelayNoOrPriorityHandleMgrBase()
    {
        this.mLoopDepth = new LoopDepth();
        this.mLoopDepth.setZeroHandle(this, null, 0);
    }

    public void init()
    {

    }

    public void dispose()
    {

    }

    protected void addObject(IDelayHandleItem delayObject, float priority)
    {
        if(this.mLoopDepth.isInDepth())
        {
            if (!this.mDeferredAddQueue.Contains((INoOrPriorityObject)delayObject))        // 如果添加列表中没有
            {
                if (this.mDeferredDelQueue.Contains((INoOrPriorityObject)delayObject))     // 如果已经添加到删除列表中
                {
                    this.mDeferredDelQueue.removeNoOrPriorityObject((INoOrPriorityObject)delayObject);
                }

                this.mDeferredAddQueue.addNoOrPriorityObject((INoOrPriorityObject)delayObject, 0.0f);
            }
        }
    }

    protected void removeObject(IDelayHandleItem delayObject)
    {
        if (this.mLoopDepth.isInDepth())
        {
            if (!this.mDeferredDelQueue.Contains((INoOrPriorityObject)delayObject))
            {
                if (this.mDeferredAddQueue.Contains((INoOrPriorityObject)delayObject))    // 如果已经添加到删除列表中
                {
                    this.mDeferredAddQueue.removeNoOrPriorityObject((INoOrPriorityObject)delayObject);
                }

                delayObject.setClientDispose(true);

                this.mDeferredDelQueue.addNoOrPriorityObject((INoOrPriorityObject)delayObject, 0.0f);
            }
        }
    }

    public void call(IDispatchObject dispObj, int eventId)
    {
        this.processDelayObjects();
    }

    private void processDelayObjects()
    {
        int idx = 0;
        // len 是 Python 的关键字
        int elemLen = 0;

        if (!this.mLoopDepth.isInDepth())       // 只有全部退出循环后，才能处理添加删除
        {
            if (this.mDeferredAddQueue.Count() > 0)
            {
                idx = 0;
                elemLen = this.mDeferredAddQueue.Count();

                while(idx < elemLen)
                {
                    this.addObject((IDelayHandleItem)this.mDeferredAddQueue.get(idx), 0.0f);

                    idx += 1;
                }

                this.mDeferredAddQueue.Clear();
            }

            if (this.mDeferredDelQueue.Count() > 0)
            {
                idx = 0;
                elemLen = this.mDeferredDelQueue.Count();

                while(idx < elemLen)
                {
                    this.removeObject((IDelayHandleItem)this.mDeferredDelQueue.get(idx));

                    idx += 1;
                }

                this.mDeferredDelQueue.Clear();
            }
        }
    }

    protected void incDepth()
    {
        this.mLoopDepth.incDepth();
    }

    protected void decDepth()
    {
        this.mLoopDepth.decDepth();
    }

    protected boolean isInDepth()
    {
        return this.mLoopDepth.isInDepth();
    }
}