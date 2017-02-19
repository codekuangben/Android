package SDK.Lib.FrameHandle;

public class TextCompTimer extends DaoJiShiTimer
{
    //protected Text mText;

    @Override
    protected void onPreCallBack()
    {
        super.onPreCallBack();
        //this.mText.text = UtilLogic.formatTime((int)this.mCurRunTime);
    }
}