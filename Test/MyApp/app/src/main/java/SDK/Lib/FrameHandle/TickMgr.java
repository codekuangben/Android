/**
* @brief 心跳管理器
*/
package SDK.Lib.FrameHandle;

import SDK.Lib.DataStruct.MList;
import SDK.Lib.DelayHandle.DelayHandleMgrBase;
import SDK.Lib.DelayHandle.IDelayHandleItem;
import SDK.Lib.EventHandle.ICalleeObjectNoRetNoParam;
import SDK.Lib.Tools.UtilApi;

public class TickMgr extends DelayHandleMgrBase
{
    protected MList<TickProcessObject> mTickList;

    public TickMgr()
    {
        this.mTickList = new MList<TickProcessObject>();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void dispose()
    {
        this.mTickList.Clear();
    }

    public void addTick(ITickedObject tickObj)
    {
        this.addTick(tickObj, 0);
    }

    public void addTick(ITickedObject tickObj, float priority)
    {
        this.addObject((IDelayHandleItem)tickObj, priority);
    }

    @Override
    protected void addObject(IDelayHandleItem delayObject)
    {
        this.addObject(delayObject, 0);
    }

    @Override
    protected void addObject(IDelayHandleItem delayObject, float priority)
    {
        if (this.mLoopDepth.isInDepth())
        {
            super.addObject(delayObject, priority);
        }
        else
        {
            int position = -1;
            int idx = 0;
            int elemLen = this.mTickList.Count();

            while(idx < elemLen)
            {
                if (this.mTickList.get(idx) == null)
                {
                    continue;
                }

                if (this.mTickList.get(idx).mTickObject == delayObject)
                {
                    return;
                }

                if (this.mTickList.get(idx).mPriority < priority)
                {
                    position = idx;
                    break;
                }

                idx += 1;
            }

            TickProcessObject processObject = new TickProcessObject();
            processObject.mTickObject = (ITickedObject)delayObject;
            processObject.mPriority = priority;

            if (position < 0 || position >= this.mTickList.Count())
            {
                this.mTickList.Add(processObject);
            }
            else
            {
                this.mTickList.Insert(position, processObject);
            }
        }
    }

    public void removeTick(ITickedObject tickObj)
    {
        this.removeObject((IDelayHandleItem)tickObj);
    }

    @Override
    protected void removeObject(IDelayHandleItem delayObject)
    {
        if (this.mLoopDepth.isInDepth())
        {
            super.removeObject(delayObject);
        }
        else
        {
            for(TickProcessObject item : this.mTickList.list())
            {
                if (UtilApi.isAddressEqual(item.mTickObject, delayObject))
                {
                    this.mTickList.Remove(item);
                    break;
                }
            }
        }
    }

    public void Advance(float delta)
    {
        this.mLoopDepth.incDepth();

        //foreach (TickProcessObject tk in this.mTickList.list())
        //{
        //    if (!(tk.mTickObject as IDelayHandleItem).isClientDispose())
        //    {
        //        (tk.mTickObject as ITickedObject).onTick(delta);
        //    }
        //}
        this.onPreAdvance(delta);
        this.onExecAdvance(delta);
        this.onPostAdvance(delta);

        this.mLoopDepth.decDepth();
    }

    protected void onPreAdvance(float delta)
    {

    }

    protected void onExecAdvance(float delta)
    {
        int idx = 0;
        int count = this.mTickList.Count();
        ITickedObject tickObject = null;

        while (idx < count)
        {
            tickObject = this.mTickList.get(idx).mTickObject;

            if (!((IDelayHandleItem)tickObject).isClientDispose())
            {
                tickObject.onTick(delta);
            }

            ++idx;
        }
    }

    protected void onPostAdvance(float delta)
    {

    }
}