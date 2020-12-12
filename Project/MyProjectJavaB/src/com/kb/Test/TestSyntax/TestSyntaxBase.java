package com.kb.Test.TestSyntax;

import java.lang.reflect.Array;

public class TestSyntaxBase<T>
{
    public Class<T> getTemplateA()
    {
        return null;
    }

    public void init()
    {
        T[] array = (T[]) Array.newInstance(this.getTemplateA(), 10);
        System.out.println(array);
    }
}