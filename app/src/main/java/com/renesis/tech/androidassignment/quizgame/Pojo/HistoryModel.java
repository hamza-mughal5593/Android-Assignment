package com.renesis.tech.androidassignment.quizgame.Pojo;

public class HistoryModel {
    int id=0;
    String total_percentage;
    String cate_name;

    public HistoryModel(int id, String total_percentage, String cate_name) {
        this.id = id;
        this.total_percentage = total_percentage;
        this.cate_name = cate_name;
    }

    public int getId() {
        return id;
    }

    public String getTotal_percentage() {
        return total_percentage;
    }

    public String getCate_name() {
        return cate_name;
    }
}
