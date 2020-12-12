package com.kb.mylibs.FileVisitor;

import android.app.Activity;
import android.content.Context;
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
        //_NativeAssetManager.close();
        this._NativeAssetManager = null;
    }

    public void setContext(Context context)
    {
        if (null != context)
        {
            this._NativeAssetManager = context.getAssets();
            //this._NativeAssetManager = activity.getResources().getAssets();
        }
        else
        {
            UtilLog.log("setActivity, is null");
        }
    }

    public void setActivity(Activity activity)
    {
        if (null != activity)
        {
            this._NativeAssetManager = activity.getAssets();
            //this._NativeAssetManager = activity.getResources().getAssets();
        }
        else
        {
            UtilLog.log("setActivity, is null");
        }
    }

    public String[] list(String path)
    {
        String[] ret = null;

        if (null != this._NativeAssetManager)
        {
            try
            {
                ret = this._NativeAssetManager.list(path);
            }
            catch (IOException error)
            {

            }
        }

        return ret;
    }

    public InputStream open(String path)
    {
        InputStream inputStream = null;

        if (null != this._NativeAssetManager)
        {
            try
            {
                inputStream = this._NativeAssetManager.open(path);
            }
            catch (IOException error)
            {
                UtilLog.log("open, is error");
            }
        }

        return inputStream;
    }
}