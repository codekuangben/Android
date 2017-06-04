package AppFrame.UI.UINet;

import android.view.View;
import android.widget.TextView;

import com.bbb.aaa.myapp.NetActivity;
import com.bbb.aaa.myapp.R;

import SDK.Lib.NetWork.NetPing;
import SDK.Lib.Tools.UtilApi;
import SDK.Lib.UI.UICore.Form;

public class UINet extends Form
{
    protected TextView mTextView;

    @Override
    public void onInit()
    {
        UtilApi.findViewById(this.mActivity, UINetCV.BtnId_TestPing).setOnClickListener((NetActivity)this.mActivity);
        this.mTextView = (TextView)UtilApi.findViewById(this.mActivity, R.id.NetTextView_Log);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.NetBtnId_TestPing:
                //UtilApi.startActivity(this.mActivity, "com.bbb.aaa.myapp.MainActivity");
                //UtilApi.finishActivity(this.mActivity);
                this.testPing();
                break;
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