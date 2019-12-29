package com.kb.mylibs.Auxiliary.AuxUi;

import android.app.Activity;
import android.view.View;

public class AuxWindow
{
    protected Activity mParentActivity;
    protected View mNativeView;

    public AuxWindow()
    {

    }

    public Activity getActivity()
    {
        return this.mParentActivity;
    }

    public void setActivity(Activity value)
    {
        this.mParentActivity = value;
    }

    public View getView()
    {
        return this.mNativeView;
    }

    public void setView(View value)
    {
        this.mNativeView = value;
    }
}