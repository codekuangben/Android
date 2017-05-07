package AppFrame.UI.UIMain;

import android.view.View;

import com.bbb.aaa.myapp.MainActivity;
import com.bbb.aaa.myapp.R;

import SDK.Lib.Tools.UtilApi;
import SDK.Lib.UI.UICore.Form;

public class UIMain extends Form
{
    protected MainActivity mMainActivity;

    @Override
    public void onInit()
    {
        UtilApi.findViewById(this.mMainActivity, UIMainCV.BtnId_TestPing).setOnClickListener(this.mMainActivity);
    }

    public void setMainActivity(MainActivity value)
    {
        this.mMainActivity = value;
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.BtnTestPing:
                //UtilApi.startActivity(this.mMainActivity, "com.bbb.aaa.myapp.NetActivity");
                UtilApi.openURL(this.mMainActivity, "http://www.baidu.com");
                break;
        }
    }
}