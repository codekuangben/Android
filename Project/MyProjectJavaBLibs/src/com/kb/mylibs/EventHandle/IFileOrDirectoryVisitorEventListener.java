package com.kb.mylibs.EventHandle;

public interface IFileOrDirectoryVisitorEventListener
{
    public void call(String absolutionPath, String fileOrDirName);
}
