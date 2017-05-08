package SDK.Lib.FrameHandle;

import SDK.Lib.DataStruct.NoOrPriorityList.INoOrPriorityObject;
import SDK.Lib.DelayHandle.DelayNoPriorityHandleMgr;
import SDK.Lib.DelayHandle.IDelayHandleItem;
import SDK.Lib.FrameWork.Ctx;
import SDK.Lib.FrameWork.MacroDef;
import SDK.Lib.Log.LogTypeId;

// 每一帧执行的对象管理器
public class TickObjectNoPriorityMgr extends DelayNoPriorityHandleMgr implements ITickedObject, IDelayHandleItem, INoOrPriorityObject
{
    public TickObjectNoPriorityMgr()
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
        super.dispose();
    }

    public void setClientDispose(boolean isDispose)
    {

    }

    public boolean isClientDispose()
    {
        return false;
    }

    public void onTick(float delta, TickMode tickMode)
    {
        this.incDepth();

        this.onPreAdvance(delta, tickMode);
        this.onExecAdvance(delta, tickMode);
        this.onPostAdvance(delta, tickMode);

        this.decDepth();
    }

    protected void onPreAdvance(float delta, TickMode tickMode)
    {

    }

    protected void onExecAdvance(float delta, TickMode tickMode)
    {
        int idx = 0;
        int count = this.mNoOrPriorityList.Count();
        ITickedObject tickObject = null;

        while (idx < count)
        {
            tickObject = (ITickedObject)this.mNoOrPriorityList.get(idx);

            if (null != (IDelayHandleItem)tickObject)
            {
                if (!((IDelayHandleItem)tickObject).isClientDispose())
                {
                    tickObject.onTick(delta, tickMode);
                }
            }
            else
            {
                if (MacroDef.ENABLE_LOG)
                {
                    Ctx.mInstance.mLogSys.log("TickObjectNoPriorityMgr::onExecAdvance, failed", LogTypeId.eLogCommon);
                }
            }

            ++idx;
        }
    }

    protected void onPostAdvance(float delta, TickMode tickMode)
    {

    }
}