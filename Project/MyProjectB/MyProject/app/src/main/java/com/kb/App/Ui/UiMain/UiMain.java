package com.kb.App.Ui.UiMain;

import android.view.View;

import com.kb.R;

import com.kb.mylibs.Auxiliary.AuxUi.AuxButton;
import com.kb.mylibs.EventHandle.IDispatchObject;
import com.kb.mylibs.Tools.UtilAndroidLibsWrap;
import com.kb.mylibs.Ui.Base.Form;

public class UiMain extends Form
{
    protected AuxButton mTestButton;

    @Override
    public void onInit()
    {
        this.mTestButton = new AuxButton();
        this.mTestButton.setActivity(this.mActivity);
        this.mTestButton.setView(UtilAndroidLibsWrap.findViewById(this.mActivity, UiMainCV.BtnId_TestPing));
        this.mTestButton.addClickHandle(this, null, 0);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.BtnTestPing:
                UtilAndroidLibsWrap.startActivity(this.mActivity, "com.kb.NetActivity");
                //UtilAndroidLibsWrap.openURL(this.mActivity, "http://www.baidu.com");
                break;
        }
    }

    @Override
    public void call(IDispatchObject dispObj)
    {
        super.call(dispObj);


    }
}