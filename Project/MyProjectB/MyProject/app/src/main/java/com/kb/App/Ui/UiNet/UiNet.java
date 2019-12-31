package com.kb.App.Ui.UiNet;

import android.view.View;
import android.widget.TextView;

import com.kb.R;
import com.kb.mylibs.NetWork.NetPing;
import com.kb.mylibs.Tools.UtilAndroidLibsWrap;
import com.kb.mylibs.Ui.Base.Form;

public class UiNet extends Form implements View.OnClickListener
{
    protected TextView mTextView;

    @Override
    public void onInit()
    {
        // 新版本 sdk 修改，这个会编译报错
        //UtilAndroidLibsWrap.findViewById(this.mActivity, UiNetCV.BtnId_TestPing).setOnClickListener((NetActivity)this.mActivity);
        UtilAndroidLibsWrap.findViewById(this.mActivity, UiNetCV.BtnId_TestPing).setOnClickListener(this);
        this.mTextView = (TextView)UtilAndroidLibsWrap.findViewById(this.mActivity, R.id.NetTextView_Log);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.NetBtnId_TestPing:
            {
                UtilAndroidLibsWrap.startActivity(this.mActivity, "com.kb.App.Ui.UiMain.MainActivity");
                //UtilAndroidLibsWrap.finishActivity(this.mActivity);
                this.testPing();
                break;
            }
        }
    }

    public void testPing()
    {
        NetPing netPing = new NetPing();
        StringBuffer stringBuffer = new StringBuffer();
        //netPing.ping("http://www.baidu.com", 10, stringBuffer);   // 不能添加 http ，添加了 ping 就不通了
        netPing.ping("www.baidu.com", 10, stringBuffer);
        this.mTextView.append(stringBuffer);
    }
}