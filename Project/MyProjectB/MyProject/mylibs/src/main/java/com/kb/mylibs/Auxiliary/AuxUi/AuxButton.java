package com.kb.mylibs.Auxiliary.AuxUi;

import android.view.View;

import com.kb.mylibs.EventHandle.AddOnceEventDispatch;
import com.kb.mylibs.EventHandle.EventDispatch;
import com.kb.mylibs.EventHandle.ICalleeObject;
import com.kb.mylibs.EventHandle.IDispatchObject;

public class AuxButton extends AuxWindow implements View.OnClickListener
{
    protected AddOnceEventDispatch mClickDispatch;

    public AuxButton()
    {

    }

    @Override
    public void setView(View value)
    {
        super.setView(value);

        if (null != this.mNativeView)
        {
            this.mNativeView.setOnClickListener(this);
        }
    }

    public void onClick(View v)
    {

    }

    public void addEventHandle()
    {

    }

    public void addClickHandle(ICalleeObject ptr, IDispatchObject dispObj, int eventId)
    {
        if (null != this.mClickDispatch)
        {
            this.mClickDispatch = new AddOnceEventDispatch();
        }

        this.mClickDispatch.addEventHandle(ptr, dispObj, eventId);
    }
}