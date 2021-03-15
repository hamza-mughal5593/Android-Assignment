package com.renesis.tech.androidassignment.quizgame.Pojo;

public class CategoryLevelModel {
    int id;
    int cat_icon;
    String check_ad;
    String check_level;

    public CategoryLevelModel(int id, int cat_icon, String check_ad, String check_level) {
        this.id = id;
        this.cat_icon = cat_icon;
        this.check_ad = check_ad;
        this.check_level = check_level;
    }

    public String getCheck_level() {
        return check_level;
    }

    public void setCheck_ad(String check_ad) {
        this.check_ad = check_ad;
    }

    public void setCheck_level(String check_level) {
        this.check_level = check_level;
    }

    public String getCheck_ad() {
        return check_ad;
    }

    public int getId() {
        return id;
    }

    public int getCat_icon() {
        return cat_icon;
    }
}
