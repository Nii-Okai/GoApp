package com.wedidsystem.goapp.model;

public class History {
    int id;
    String keyword;
    String period;

    public History(int id, String keyword, String period) {
        this.id = id;
        this.keyword = keyword;
        this.period = period;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
