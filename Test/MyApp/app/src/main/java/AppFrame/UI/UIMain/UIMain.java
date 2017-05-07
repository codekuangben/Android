package AppFrame.UI.UIMain;

import com.bbb.aaa.myapp.MainActivity;

import SDK.Lib.Tools.UtilApi;
import SDK.Lib.UI.UICore.Form;

public class UIMain extends Form
{
    protected MainActivity mMainActivity;

    @Override
    public void onInit()
    {
        UtilApi.findViewById(this.mMainActivity, this.mMainActivity.R.id.myButton).setOnClickListener(this.mMainActivity);
    }

    public void setMainActivity(MainActivity value)
    {
        this.mMainActivity = value;
    }
}