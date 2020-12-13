package com.kb.mylibs.Tools;

public class UtilDebug
{
    public static void asset(Boolean condition, String message)
    {
        assert condition : message;
    }
}