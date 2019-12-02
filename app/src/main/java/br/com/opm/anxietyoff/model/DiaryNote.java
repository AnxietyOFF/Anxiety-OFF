package br.com.opm.anxietyoff.model;

import android.icu.util.GregorianCalendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class DiaryNote {

    private String id, title, subTitle, text, preview, date;
    private Calendar calendar;
    private boolean starred;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DiaryNote(String id, String title, String subTitle, String text, Calendar calendar, boolean starred) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.text = text;
        this.preview=text.substring(0, 100);
        this.calendar = calendar;
        date=calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+calendar.get(Calendar.YEAR);
        this.starred = starred;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getText() {
        return text;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public boolean isStarred() {
        return starred;
    }

    public String getPreview() {
        return preview;
    }

    public String getDate() {
        return date;
    }
}
