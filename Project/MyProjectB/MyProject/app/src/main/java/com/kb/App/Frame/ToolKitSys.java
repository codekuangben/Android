package com.kb.App.Frame;

import com.kb.mylibs.Core.GObject;

public class ToolKitSys extends GObject
{
    public static ToolKitSys mInstance;

    public static ToolKitSys instance()
    {
        if (null == ToolKitSys.mInstance)
        {
            ToolKitSys.mInstance = new ToolKitSys();
        }

        return ToolKitSys.mInstance;
    }

    public void init()
    {

    }

    public void dispose()
    {

    }
}