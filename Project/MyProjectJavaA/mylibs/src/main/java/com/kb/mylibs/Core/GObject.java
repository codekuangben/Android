package com.kb.mylibs.Core;

public class GObject
{
    protected String mTypeId;     // 名字

    public GObject()
    {
        this.mTypeId = "GObject";
    }

    public String getTypeId()
    {
        return this.mTypeId;
    }
}