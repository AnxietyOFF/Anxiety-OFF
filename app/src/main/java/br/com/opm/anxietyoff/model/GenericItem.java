package br.com.opm.anxietyoff.model;

import android.view.View;

import java.io.Serializable;

public class GenericItem {

    private String title, subtitle;
    private View.OnClickListener onClickListener;
    private int imageId;

    public GenericItem(String title, String subtitle, View.OnClickListener onClickListener, int imageId) {
        this.title = title;
        this.subtitle = subtitle;
        this.onClickListener = onClickListener;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getImageId() {
        return imageId;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}
