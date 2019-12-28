package App.Frame;

import Libs.Core.GObject;

public class ToolSetSys extends GObject
{
    public static ToolSetSys mInstance;

    public static ToolSetSys instance()
    {
        if (null == ToolSetSys.mInstance)
        {
            ToolSetSys.mInstance = new ToolSetSys();
        }

        return ToolSetSys.mInstance;
    }

    public void init()
    {

    }

    public void dispose()
    {

    }
}