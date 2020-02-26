package com.wedidsystem.goapp.model;

public class Item {
    int id;
    String keyword;
    String period;
    long expirationTime;

    public Item(int id, String keyword, String period, long expirationTime) {
        this.id = id;
        this.keyword = keyword;
        this.period = period;
        this.expirationTime = expirationTime;
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

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
