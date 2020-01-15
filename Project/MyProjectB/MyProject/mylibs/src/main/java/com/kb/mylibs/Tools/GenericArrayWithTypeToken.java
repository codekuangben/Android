package com.kb.mylibs.Tools;

import java.lang.reflect.Array;

/**
 * @url https://www.cnblogs.com/Hdaydayup/p/7472674.html
 */
public class GenericArrayWithTypeToken<T>
{
    public GenericArrayWithTypeToken(Class<T> type, int size)
    {
        T[] array = (T[]) Array.newInstance(type, size);
    }
}