package SDK.Lib.DelayHandle;

import SDK.Lib.DataStruct.NoOrPriorityList.INoOrPriorityList;
import SDK.Lib.DataStruct.NoOrPriorityList.INoOrPriorityObject;
import SDK.Lib.FrameHandle.ITickedObject;
import SDK.Lib.FrameWork.Ctx;
import SDK.Lib.FrameWork.MacroDef;
import SDK.Lib.Log.LogTypeId;

/**
 * @brief 延迟优先级处理管理器
 */
public class DelayNoOrPriorityHandleMgr extends DelayNoOrPriorityHandleMgrBase
{
    protected INoOrPriorityList mNoOrPriorityList;

    public DelayNoOrPriorityHandleMgr()
    {

    }

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void dispose()
    {
        this.mNoOrPriorityList.Clear();
    }

    @Override
    protected void addObject(IDelayHandleItem delayObject, float priority)
    {
        if (null != delayObject)
        {
            if (this.isInDepth())
            {
                super.addObject(delayObject, priority);
            }
            else
            {
                this.mNoOrPriorityList.addNoOrPriorityObject((INoOrPriorityObject)delayObject, priority);
            }
        }
        else
        {
            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log("DelayPriorityHandleMgr::addObject, failed", LogTypeId.eLogCommon);
            }
        }
    }

    @Override
    protected void removeObject(IDelayHandleItem delayObject)
    {
        if (null != delayObject)
        {
            if (this.isInDepth())
            {
                super.removeObject(delayObject);
            }
            else
            {
                this.mNoOrPriorityList.removeNoOrPriorityObject((INoOrPriorityObject)delayObject);
            }
        }
        else
        {
            if (MacroDef.ENABLE_LOG)
            {
                Ctx.mInstance.mLogSys.log("DelayPriorityHandleMgr::removeObject, failed", LogTypeId.eLogCommon);
            }
        }
    }

    public void addNoOrPriorityObject(INoOrPriorityObject priorityObject, float priority)
    {
        this.addObject((IDelayHandleItem)priorityObject, priority);
    }

    public void removeNoOrPriorityObject(ITickedObject tickObj)
    {
        this.removeObject((IDelayHandleItem)tickObj);
    }
}