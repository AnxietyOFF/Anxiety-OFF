package br.com.opm.anxietyoff.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Questions {

    @SerializedName("titles")
    @Expose
    private List<String> titles;

    public Questions(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

}
