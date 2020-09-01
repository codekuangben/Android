package com.kb.mylibs.FileVisitor;

import com.kb.mylibs.Core.GObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MBufferFileStream extends GObject
{
    protected String mFilePath;
    protected BufferedWriter mBufferedWriter;
    protected BufferedReader mBufferedReader;

    public MBufferFileStream(String fileName)
    {
        try
        {
            FileWriter writer = new FileWriter(mFilePath, true);
            BufferedWriter out = new BufferedWriter(new FileWriter(mFilePath));
            FileReader reader = new FileReader(mFilePath);
            BufferedReader in = new BufferedReader(new FileReader(mFilePath));
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public void init()
    {

    }

    public void dispose()
    {

    }

    public String readText()
    {
        try
        {
            mBufferedReader.read();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        return "";
    }
}
