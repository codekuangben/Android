package com.bbb.aaa.myapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.view.View.OnClickListener;

import AppFrame.UI.UINet.UINet;
import AppFrame.UI.UINet.UINetCV;

public class NetActivity extends AppCompatActivity implements OnClickListener{
    protected UINet mUINet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
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

    protected void init()
    {
        UINetCV.BtnId_TestPing = R.id.NetBtnId_TestPing;

        this.mUINet = new UINet();
        this.mUINet.setActivity(this);
        this.mUINet.init();
    }

    @Override
    public void onClick(View view)
    {
        this.mUINet.onClick(view);
    }
}
