package com.soufang.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String format1 = "yyyy-MM-dd";

    public static String format2 = "yyyy-MM-dd HH:mm:ss";

    public static long getDate(){
        return System.currentTimeMillis();
    }

    public static String date2String(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date string2Date(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    public static Date getToday(){
        return new Date();
    }

    public static String getToday(String format) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static Date getDefaultDate(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format1);
            return sdf.parse("2000-01-01");
        }catch (Exception e){

        }
        return new Date();
    }

    public static int getDateDifference(Date date1, Date date2, String timeType) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format2);
        int between = (int) (date1.getTime() - date2.getTime());
        int difference = 0;
        switch (timeType) {
            case "day":
                difference = between / (24 * 60 * 60 * 1000);
                break;
            case "hour":
                difference = between / (60 * 60 * 1000);
                break;
            case "min":
                difference = between / (60 * 1000);
                break;
            case "s":
                difference = between / 1000;
        }
        return difference;
    }

    public static int getDateDifference(String date1, String date2, String timeType) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format2);
        int between = (int) (sdf.parse(date1).getTime() - sdf.parse(date2).getTime());
        int difference = 0;
        switch (timeType) {
            case "day":
                difference = between / (24 * 60 * 60 * 1000);
                break;
            case "hour":
                difference = between / (60 * 60 * 1000);
                break;
            case "min":
                difference = between / (60 * 1000);
                break;
        }
        return difference;
    }

}
