package com.bookhotel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringProccessUtil {
    public static Date StringToDate(String str) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date format. Use yyyy/mm/dd.");
        }
        return date;
    }

    public static String DateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        return df.format(date);
    }
    public static Integer daysBetween2Dates(Date x,Date y){
        return Math.toIntExact((y.getTime() - x.getTime()) / (24 * 3600 * 1000));
    }
}
