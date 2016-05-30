package com.example.utils;

/**
 * 将2016-05-09 23:00:00 转换成 May 09.2016格式
 * Created by RImpression on 2016/5/30 0030.
 */
public class DateFormatUtil {

    public String setDataFormat(String s) {
        String year = s.substring(0,4);
        String month = s.substring(5,7);
        String day = s.substring(8,10);
        String m = setMonthFormat(month);
        //System.out.println(m+" "+day+"."+year);
        return m+" "+day+"."+year;
    }

    private String setMonthFormat(String month) {
        if (month.equals("01")){
            return "Jan";
        } else if (month.equals("02")) {
            return "Feb";
        } else if (month.equals("03")) {
            return "Mar";
        } else if (month.equals("04")) {
            return "Apr";
        } else if (month.equals("05")) {
            return "May";
        } else if (month.equals("06")) {
            return "Jun";
        } else if (month.equals("07")) {
            return "July";
        } else if (month.equals("08")) {
            return "Aug";
        } else if (month.equals("09")) {
            return "Set";
        } else if (month.equals("10")) {
            return "Oct";
        } else if (month.equals("11")) {
            return "Nov";
        } else if (month.equals("12")) {
            return "Dec";
        }
        return null;
    }
}
