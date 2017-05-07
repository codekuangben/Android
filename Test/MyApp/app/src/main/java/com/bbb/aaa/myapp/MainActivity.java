package com.bbb.aaa.myapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View.OnClickListener;

import AppFrame.App.MyApp;
import AppFrame.UI.UIMain.UIMain;
import AppFrame.UI.UIMain.UIMainCV;
import SDK.Lib.Tools.UtilApi;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    protected UIMain mUIMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void init()
    {
        MyApp myApp = new MyApp();
        myApp.init();

        UIMainCV.BtnId_TestPing = R.id.BtnTestPing;

        this.mUIMain = new UIMain();
        this.mUIMain.setMainActivity(this);
        this.mUIMain.init();
    }

    @Override
    public void onClick(View view)
    {
        this.mUIMain.onClick(view);
    }
}
