package Libs.Ui.Base;

import android.app.Activity;

import Libs.Core.GObject;

public class Form extends GObject
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
}