package com.wofdev.wofmap.lib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jb on 02.12.17.
 */
public class Log {

    /**
     * Вывод с Тэгом
     *
     * @param tag
     * @param msg
     */
    public static void dt(String tag, String msg) {
        dt(tag + ": " + msg);
    }

    /**
     * Вывод пустой строки с датой.
     */
    public static void dt() {
        dt("");
    }

    /**
     * Вывод сообщения с датой
     *
     * @param msg
     */
    public static void dt(String msg) {
        DateFormat dateFormat = new SimpleDateFormat("[yyMMdd HH:mm:ss.SSS]: ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
        d(dateFormat.format(new Date()) + msg);
    }

    /**
     * простой вывод сообщения
     *
     * @param msg
     */
    public static void d(String msg) {
        System.out.println(msg);
    }

    /**
     * Вывод в лог ошибок
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        DateFormat dateFormat = new SimpleDateFormat("[yyMMdd HH:mm:ss.SSS]: ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
        System.err.println(dateFormat.format(new Date()) + tag + ": " + msg);
    }
}
