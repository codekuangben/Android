package com.kb.App.Test.TestSyntax;

import java.lang.reflect.ParameterizedType;

public class TestSyntaxBase_String extends TestSyntaxBase<String>
{
    @Override
    public Class<String> getTemplateA()
    {
        //return String.class;
        Class<String> typeInfo = (Class <String>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return typeInfo;
    }
}