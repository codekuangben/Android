package com.kb.mylibs.EventHandle;

import com.kb.mylibs.DataStruct.MDictionary;
import com.kb.mylibs.Log.UtilLog;

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
    public void addEventDispatch(int groupId, EventDispatch disp)
    {
        if (!this.mGroupID2DispatchDic.ContainsKey(groupId))
        {
            this.mGroupID2DispatchDic.set(groupId, disp);
        }
    }

    public void addEventHandle(
            int groupId,
            IEventListener eventListener,
            IDispatchObject eventHandle,
            int eventId
    )
    {
        // 如果没有就创建一个
        if (!this.mGroupID2DispatchDic.ContainsKey(groupId))
        {
            addEventDispatch(groupId, new EventDispatch());
        }

        this.mGroupID2DispatchDic.get(groupId).addEventHandle(
                eventListener,
                eventHandle,
                eventId
        );
    }

    public void removeEventHandle(
            int groupId,
            IEventListener eventListener,
            IDispatchObject eventHandle,
            int eventId
    )
    {
        if (this.mGroupID2DispatchDic.ContainsKey(groupId))
        {
            this.mGroupID2DispatchDic.get(groupId).removeEventHandle(
                    eventListener,
                    eventHandle,
                    eventId
            );

            // 如果已经没有了
            if (!this.mGroupID2DispatchDic.get(groupId).hasEventHandle())
            {
                this.mGroupID2DispatchDic.Remove(groupId);
            }
        }
        else
        {

        }
    }

    public void dispatchEvent(int groupId, IDispatchObject dispatchObject)
    {
        this.mIsInLoop = true;

        if (this.mGroupID2DispatchDic.ContainsKey(groupId))
        {
            this.mGroupID2DispatchDic.get(groupId).dispatchEvent(dispatchObject);
        }
        else
        {
            UtilLog.log("dispatchEvent cannot find group");
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

    public void clearGroupEventHandle(int groupId)
    {
        if (!this.mIsInLoop)
        {
            if (this.mGroupID2DispatchDic.ContainsKey(groupId))
            {
                this.mGroupID2DispatchDic.get(groupId).clearEventHandle();
                this.mGroupID2DispatchDic.Remove(groupId);
            }
            else
            {

            }
        }
        else
        {

        }
    }

    public boolean hasEventHandle(int groupId)
    {
        if(this.mGroupID2DispatchDic.ContainsKey(groupId))
        {
            return this.mGroupID2DispatchDic.get(groupId).hasEventHandle();
        }

        return false;
    }
}