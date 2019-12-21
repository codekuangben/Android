package Libs.Ui.Base;

import android.app.Activity;

import Libs.Core.GObject;
import Libs.EventHandle.ICalleeObject;
import Libs.EventHandle.IDispatchObject;

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

    public void call(IDispatchObject dispObj)
    {

    }
}