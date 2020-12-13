package com.kb.mylibs.Tools;

public class UtilLog
{
    public static void log(LogTypeId logTypeId, String message)
    {
        System.out.println(message);
    }

    public static void logFormat(LogTypeId logTypeId, String format, Object... args)
    {
        String message = String.format(format, args);
        UtilLog.log(logTypeId, message);
    }

    public static void warn(LogTypeId logTypeId, String message)
    {
        System.out.println(message);
    }

    public static void warnFormat(LogTypeId logTypeId, String format, Object... args)
    {
        String message = String.format(format, args);
        UtilLog.warn(logTypeId, message);
    }

    public static void error(LogTypeId logTypeId, String message)
    {
        System.out.println(message);
    }

    public static void errorFormat(LogTypeId logTypeId, String format, Object... args)
    {
        String message = String.format(format, args);
        UtilLog.error(logTypeId, message);
    }
}