package App.Ui.UiMain;

import android.view.View;

import com.bbb.aaa.myapp.R;

import Libs.Tools.UtilApi;
import Libs.Ui.UiCore.Form;

public class UiMain extends Form
{
    @Override
    public void onInit()
    {
        UtilApi.findViewById(this.mActivity, UiMainCV.BtnId_TestPing).setOnClickListener((MainActivity)this.mActivity);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.BtnTestPing:
                UtilApi.startActivity(this.mActivity, "com.bbb.aaa.myapp.NetActivity");
                //UtilApi.openURL(this.mActivity, "http://www.baidu.com");
                break;
        }
    }
}