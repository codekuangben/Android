package SDK.Lib.EventHandle;

import SDK.Lib.DataStruct.MDictionary;

public class EventDispatchGroup
{
    protected MDictionary<Integer, EventDispatch> mGroupID2DispatchDic;
    protected boolean mIsInLoop;       // 是否是在循环遍历中

    public EventDispatchGroup()
    {
        this.mGroupID2DispatchDic = new MDictionary<Integer, EventDispatch>();
        this.mIsInLoop = false;
    }

    // 添加分发器
    public void addEventDispatch(int groupID, EventDispatch disp)
    {
        if (!this.mGroupID2DispatchDic.ContainsKey(groupID))
        {
            this.mGroupID2DispatchDic.set(groupID, disp);
        }
    }

    public void addEventHandle(int groupID, ICalleeObject pThis, IDispatchObject handle)
    {
        // 如果没有就创建一个
        if (!this.mGroupID2DispatchDic.ContainsKey(groupID))
        {
            addEventDispatch(groupID, new EventDispatch());
        }

        this.mGroupID2DispatchDic.get(groupID).addEventHandle(pThis, handle);
    }

    public void removeEventHandle(int groupID, ICalleeObject pThis, IDispatchObject handle)
    {
        if (this.mGroupID2DispatchDic.ContainsKey(groupID))
        {
            this.mGroupID2DispatchDic.get(groupID).removeEventHandle(pThis, handle);

            // 如果已经没有了
            if (!this.mGroupID2DispatchDic.get(groupID).hasEventHandle())
            {
                this.mGroupID2DispatchDic.Remove(groupID);
            }
        }
        else
        {

        }
    }

    public void dispatchEvent(int groupID, IDispatchObject dispatchObject)
    {
        this.mIsInLoop = true;
        if (this.mGroupID2DispatchDic.ContainsKey(groupID))
        {
            this.mGroupID2DispatchDic.get(groupID).dispatchEvent(dispatchObject);
        }
        else
        {

        }
        this.mIsInLoop = false;
    }

    public void clearAllEventHandle()
    {
        if (!this.mIsInLoop)
        {
            for (EventDispatch dispatch : this.mGroupID2DispatchDic.getValues())
            {
                dispatch.clearEventHandle();
            }

            this.mGroupID2DispatchDic.Clear();
        }
        else
        {

        }
    }

    public void clearGroupEventHandle(int groupID)
    {
        if (!this.mIsInLoop)
        {
            if (this.mGroupID2DispatchDic.ContainsKey(groupID))
            {
                this.mGroupID2DispatchDic.get(groupID).clearEventHandle();
                this.mGroupID2DispatchDic.Remove(groupID);
            }
            else
            {

            }
        }
        else
        {

        }
    }

    public boolean hasEventHandle(int groupID)
    {
        if(this.mGroupID2DispatchDic.ContainsKey(groupID))
        {
            return this.mGroupID2DispatchDic.get(groupID).hasEventHandle();
        }

        return false;
    }
}