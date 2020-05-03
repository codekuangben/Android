package com.kb.mylibs.FileVisitor;

import android.app.Activity;
import android.content.res.AssetManager;

import com.kb.mylibs.Tools.UtilLog;

import java.io.IOException;
import java.io.InputStream;

public class MAssetManager
{
    protected AssetManager _NativeAssetManager;

    public MAssetManager()
    {

    }

    public void init()
    {

    }

    public void dispose()
    {

    }

    public void setActivity(Activity activity)
    {
        if (null != activity)
        {
            this._NativeAssetManager = activity.getAssets();
        }
        else
        {
            UtilLog.log("setActivity, is null");
        }
    }

    public InputStream open(String path)
    {
        InputStream inputStream = null;

        try
        {
            inputStream = this._NativeAssetManager.open(path);
        }
        catch (IOException error)
        {
            UtilLog.log("open, is error");
        }

        return inputStream;
    }
}