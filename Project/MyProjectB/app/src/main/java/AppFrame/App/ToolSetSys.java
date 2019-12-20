package AppFrame.App;

import SDK.Lib.Core.GObject;

public class ToolSetSys extends GObject
{
    static public ToolSetSys mInstance;

    public static ToolSetSys instance()
    {
        if (null == mInstance)
        {
            mInstance = new ToolSetSys();
        }

        return mInstance;
    }

    public void init()
    {

    }

    public void dispose()
    {

    }
}