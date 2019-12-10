package br.com.opm.anxietyoff.lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFactory {

    public static String dateToString(Date date) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        return dt.format(date);
    }

    public static Date stringToDate(String a) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            return dt.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String onlyDateToString(Date date){
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        return dt.format(date);
    }

    public static String onlyTimeToString(Date date){
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
        return dt.format(date);
    }

    public static boolean isSameDate(Date date1, Date date2){
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        String a=dt.format(date1), b=dt.format(date2);

        return a.equals(b);
    }

}
