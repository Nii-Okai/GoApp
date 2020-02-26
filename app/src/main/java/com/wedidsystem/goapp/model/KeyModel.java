package com.wedidsystem.goapp.model;

public class KeyModel {

    String keyword;
    String period;

    public KeyModel(String keyword, String period) {
        this.keyword = keyword;
        this.period = period;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getPeriod() {
        return period;
    }
}
