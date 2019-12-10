package br.com.opm.anxietyoff.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Answers {

    private List<Integer> vote;
    private int sum;
    private Date date;

    public Answers() {
    }

    public Answers(List<Integer> vote, int sum, Date date) {
        this.vote = vote;
        this.sum = sum;
        this.date = date;
    }

    public Answers(int tam) {
        this.vote = new ArrayList<>();
        sum=0;
        setupVote(tam);
        setupDate();
    }

    public List<Integer> getVote() {
        return vote;
    }

    public void setVote(List<Integer> vote) {
        this.vote = vote;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setupVote(int tam) {
        for (int i = 0; i < tam; i++) vote.add(0);
    }

    public void setupSum(){
        for (int i = 0; i < vote.size(); i++) sum+=vote.get(i);
    }

    public void setupDate() {
        this.date = Calendar.getInstance().getTime();
    }
}
