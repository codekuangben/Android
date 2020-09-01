package com.kb.mylibs.Ui.Base;

import android.app.Activity;

import com.kb.mylibs.Core.GObject;
import com.kb.mylibs.EventHandle.ICalleeObject;
import com.kb.mylibs.EventHandle.IDispatchObject;

public class Form extends GObject implements ICalleeObject
{
    protected Activity mActivity;

    public Form()
    {

    }

    public void init()
    {
        this.onInit();
    }

    public void dispose()
    {

    }

    public void onInit()
    {

    }

    public void onReady()
    {

    }

    public void onShow()
    {

    }

    public void onHide()
    {

    }

    public void onExit()
    {

    }

    public void setActivity(Activity value)
    {
        this.mActivity = value;
    }

    public void call(IDispatchObject dispatchObject)
    {

    }
}