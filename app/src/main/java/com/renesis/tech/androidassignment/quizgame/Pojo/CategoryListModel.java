package com.renesis.tech.androidassignment.quizgame.Pojo;

public class CategoryListModel {
    int cat_color;
    String islocked;
    int cat_icon;
    int cat_icon_locked;
    String cat_name;
    int total_questions;
    int id;

    public CategoryListModel(int cat_color, String islocked, int cat_icon, int cat_icon_locked, String cat_name, int total_questions, int id) {
        this.cat_color = cat_color;
        this.islocked = islocked;
        this.cat_icon = cat_icon;
        this.cat_icon_locked = cat_icon_locked;
        this.cat_name = cat_name;
        this.total_questions = total_questions;
        this.id = id;
    }

    public int getCat_color() {
        return cat_color;
    }

    public int getCat_icon_locked() {
        return cat_icon_locked;
    }

    public String getIslocked() {
        return islocked;
    }

    public void setIslocked(String islocked) {
        this.islocked = islocked;
    }

    public int getId() {
        return id;
    }

    public int getCat_icon() {
        return cat_icon;
    }

    public String getCat_name() {
        return cat_name;
    }

    public int getTotal_questions() {
        return total_questions;
    }
}
