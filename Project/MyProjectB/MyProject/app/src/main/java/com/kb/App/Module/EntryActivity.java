package com.kb.App.Module;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kb.App.Frame.AppFrame;
import com.kb.R;
import com.kb.mylibs.Tools.UtilAndroidLibsWrap;

public class EntryActivity extends AppCompatActivity
{
    protected AppFrame mAppFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        this.mAppFrame = new AppFrame();
        this.mAppFrame.init();

        //UtilAndroidLibsWrap.startActivity(
        //    this,
        //    "com.kb.App.Ui.UiMain.MainActivity"
        //);
    }
}