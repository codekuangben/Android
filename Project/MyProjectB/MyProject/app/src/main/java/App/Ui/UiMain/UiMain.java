package App.Ui.UiMain;

import android.view.View;

import com.bbb.aaa.myapp.MainActivity;
import com.bbb.aaa.myapp.R;

import Libs.Tools.UtilAndroidLibsWrap;
import Libs.Ui.Base.Form;

public class UiMain extends Form
{
    @Override
    public void onInit()
    {
        UtilAndroidLibsWrap.findViewById(this.mActivity, UiMainCV.BtnId_TestPing).setOnClickListener((MainActivity)this.mActivity);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.BtnTestPing:
                UtilAndroidLibsWrap.startActivity(this.mActivity, "com.bbb.aaa.myapp.NetActivity");
                //UtilAndroidLibsWrap.openURL(this.mActivity, "http://www.baidu.com");
                break;
        }
    }
}