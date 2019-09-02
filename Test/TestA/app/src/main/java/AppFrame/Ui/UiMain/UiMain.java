package AppFrame.UI.UIMain;

import android.view.View;

import com.example.myapp.MainActivity;
import com.example.myapp.R;

import Libs.Tools.UtilApi;
import Libs.UI.UICore.Form;

public class UIMain extends Form
{
    @Override
    public void onInit()
    {
        //UtilApi.findViewById(this.mActivity, UIMainCV.BtnId_TestPing).setOnClickListener((MainActivity)this.mActivity);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            //case R.id.BtnTestPing:
            //    UtilApi.startActivity(this.mActivity, "com.bbb.aaa.myapp.NetActivity");
                //UtilApi.openURL(this.mActivity, "http://www.baidu.com");
            //    break;
        }
    }
}