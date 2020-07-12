package com.hungdt.calendarview.weekcalendar.utils;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by linechen on 2017/5/19.<br/>
 * 描述：
 * </br>
 */

public class CalendarUtil {
    public static boolean isSameDay(DateTime t1, DateTime t2){
        return t1.toString("yyyyMMdd").equals(t2.toString("yyyyMMdd"));
    }

    public static boolean isTmrDay(DateTime t){
        DateTime tmr = new DateTime();

        return true;
    }

    public static boolean isToday(DateTime t){
        return isSameDay(t, new DateTime());
    }



}
