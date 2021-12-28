package com.company;

public class BankOperation {
    private double money;
    private String currency;
    private String card;
    private String date;

    public BankOperation(double money, String currency, String card, String date) {
        this.money = money;
        this.currency = currency;
        this.card = card;
        this.date = date;
    }

    public BankOperation(BankOperation bankOperation){
        this.money = bankOperation.money;
        this.currency = bankOperation.currency;
        this.card = bankOperation.card;
        this.date = bankOperation.date;
    }

    @Override
    public String toString() {
        return "BankOperation{" +
                "money=" + money +
                ", currency='" + currency + '\'' +
                ", card='" + card + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
