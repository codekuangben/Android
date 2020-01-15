package com.kb.mylibs.Tools;

import java.lang.reflect.Array;

/**
 * @url https://www.cnblogs.com/Hdaydayup/p/7472674.html
 */
public class GenericArrayWithTypeToken<T>
{
    private T[] array;

    public GenericArrayWithTypeToken(Class<T> type, int size)
    {
        array = (T[]) Array.newInstance(type, size);
    }
}