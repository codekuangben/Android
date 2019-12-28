package App.Ui.UiMain;

import android.view.View;

import com.bbb.aaa.myapp.MainActivity;
import com.bbb.aaa.myapp.R;

import Libs.Auxiliary.AuxUi.AuxButton;
import Libs.EventHandle.IDispatchObject;
import Libs.Tools.UtilAndroidLibsWrap;
import Libs.Ui.Base.Form;

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
            //case R.id.BtnTestPing:
            //    UtilAndroidLibsWrap.startActivity(this.mActivity, "com.bbb.aaa.myapp.NetActivity");
                //UtilAndroidLibsWrap.openURL(this.mActivity, "http://www.baidu.com");
            //    break;
        }
    }

    @Override
    public void call(IDispatchObject dispObj)
    {
        super.call(dispObj);


    }
}