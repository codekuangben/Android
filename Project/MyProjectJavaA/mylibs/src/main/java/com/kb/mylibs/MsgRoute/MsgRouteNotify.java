package com.kb.mylibs.MsgRoute;

import com.kb.mylibs.DataStruct.MList;

public class MsgRouteNotify
{
    protected MList<MsgRouteDispHandle> mDispList;

    public MsgRouteNotify()
    {
        this.mDispList = new MList<MsgRouteDispHandle>();
    }

    public void addOneDisp(MsgRouteDispHandle disp)
    {
        if(!this.mDispList.Contains(disp))
        {
            this.mDispList.Add(disp);
        }
    }

    public void removeOneDisp(MsgRouteDispHandle disp)
    {
        if(this.mDispList.Contains(disp))
        {
            this.mDispList.Remove(disp);
        }
    }

    public void handleMsg(MsgRouteBase msg)
    {
        for(MsgRouteDispHandle item : this.mDispList.list())
        {
            item.handleMsg(msg);
        }

        //Ctx.msIns.mPoolSys.deleteObj(msg);
    }
}