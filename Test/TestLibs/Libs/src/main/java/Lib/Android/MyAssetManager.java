package Libs.Android;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;

public class MyAssetManager
{
    protected AssetManager mAssetManager;

    public MyAssetManager()
    {

    }

    public void setNativeAssetManager(AssetManager assetManager)
    {
        this.mAssetManager = assetManager;

        if(null == this.mAssetManager)
        {
            System.out.print("assetManager is none");
        }
    }

    /**
     * @param  filePathStartAfterAssets : 目录是相对 assets 后的目录
     * @ref http://www.360doc.com/content/18/0117/18/8279768_722757469.shtml
     * @ref http://www.technotalkative.com/android-read-file-from-assets/
     */
    public boolean isFileExist(String filePathStartAfterAssets)
    {
        try
        {
            boolean ret = false;

            String[] fileNameList = this.mAssetManager.list("");

            for (int index = 0; index < fileNameList.length; ++index)
            {
                if(filePathStartAfterAssets == fileNameList[index])
                {
                    ret = true;
                    break;
                }
            }

            return ret;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.print("isFileExist error");
            return false;
        }
    }

    public String readText(String filePathStartAfterAssets)
    {
        InputStream input;
        try
        {
            input = this.mAssetManager.open(filePathStartAfterAssets);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String text = new String(buffer);
            return text;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.print("readText error");
            return "";
        }
    }

    public byte[] readBytes(String filePathStartAfterAssets)
    {
        InputStream input;
        try
        {
            input = this.mAssetManager.open(filePathStartAfterAssets);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            return buffer;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.print("readBytes error");
            return null;
        }
    }
}