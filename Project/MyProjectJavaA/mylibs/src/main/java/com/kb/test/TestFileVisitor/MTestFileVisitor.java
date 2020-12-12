package com.kb.App.Test.TestFileVisitor;

import com.kb.App.Test.TestBase.MTestBase;
import com.kb.mylibs.FrameWork.Ctx;
import com.kb.mylibs.Tools.UtilLog;
import com.kb.mylibs.Tools.UtilSysLibsWrap;

import java.io.IOException;
import java.io.InputStream;

public class MTestFileVisitor extends MTestBase
{
    public MTestFileVisitor()
    {

    }

    @Override
    public void run()
    {
        super.run();
        this._testA();
    }

    protected void _testA()
    {
        // path 是相对目录，相对于 Android\Project\MyProjectB\MyProject\app\src\main\assets
        InputStream inputStream = Ctx.msIns.mAssetManager.open("Config/TestA.txt");

        byte[] byteArray = new byte[1024];

        while (UtilSysLibsWrap.read(inputStream, byteArray, 0, byteArray.length) > 0)
        {
            String strContent = new String(byteArray);
            UtilLog.log(strContent);
        }
//        int ret = 0;
//        try
//        {
//            if (null != inputStream && null != byteArray)
//            {
//                ret = inputStream.read(byteArray, 0, 1024);
//            }
//        }
//        catch(IOException error)
//        {
//
//        }
    }
}