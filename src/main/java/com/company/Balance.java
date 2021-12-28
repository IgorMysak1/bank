package com.company;

public class Balance {
    private double uan;
    private double eur;
    private double usd;
    private String date;

    public Balance(double uan, double eur, double usd, String date) {
        this.uan = uan;
        this.eur = eur;
        this.usd = usd;
        this.date = date;
    }

    public Balance(Balance balance) {
        this.uan = balance.uan;
        this.eur = balance.eur;
        this.usd = balance.usd;
        this.date = balance.date;
    }

    public double getUan() {
        return uan;
    }

    public void setUan(double uan) {
        this.uan = uan;
    }

    public double getEur() {
        return eur;
    }

    public void setEur(double eur) {
        this.eur = eur;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
