package com.kb.App.Frame;

import com.kb.mylibs.Core.GObject;

public class ToolKitSys extends GObject
{
    public static ToolKitSys msIns;

    public static ToolKitSys instance()
    {
        if (null == ToolKitSys.msIns)
        {
            ToolKitSys.msIns = new ToolKitSys();
        }

        return ToolKitSys.msIns;
    }

    public void init()
    {

    }

    public void dispose()
    {

    }
}