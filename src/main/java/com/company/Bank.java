package com.company;

import javafx.fxml.FXML;

import java.sql.SQLException;
import java.util.Arrays;

public class Bank {

    private String name;
    private int rate;
    private int human_trust;

    private DepositOffer[] regularDepositOffers;
    private int offers;

    public Bank(String name, int rate, int human_trust) {
        this.name = name;
        this.rate = rate;
        this.human_trust = human_trust;
    }

    public Bank(Bank bank) {
        name = bank.name;
        rate = bank.rate;
        human_trust = bank.human_trust;

        regularDepositOffers = new DepositOffer[bank.regularDepositOffers.length];

        for(int i = 0; i < regularDepositOffers.length; i++)
            regularDepositOffers[i] = new DepositOffer(bank.regularDepositOffers[i]);

        offers = bank.offers;
    }

    public void setRegularArray(int n){
        regularDepositOffers = new DepositOffer[n];
        offers = n;
    }

    public void setRegularElement(int n, double maxSum, int maxDepositTime, boolean usd,
                                  boolean uah, boolean eur, boolean terminationPossibility, double growthPercentage){
        regularDepositOffers[n] = new DepositOffer(maxSum, maxDepositTime, usd, uah, eur, terminationPossibility, growthPercentage);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getHuman_trust() {
        return human_trust;
    }

    public void setHuman_trust(int human_trust) {
        this.human_trust = human_trust;
    }

    public DepositOffer[] getRegularDepositOffers() {
        return regularDepositOffers;
    }

    public DepositOffer getRegularDepositOffers(int i) {
        return regularDepositOffers[i];
    }

    public void setRegularDepositOffers(DepositOffer[] regularDepositOffers) {
        this.regularDepositOffers = regularDepositOffers;
    }

    public int getOffers(){
        return offers;
    }


    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                ", human_trust=" + human_trust +
               // ", regularDepositOffers=" + Arrays.toString(regularDepositOffers) +
                ", offers=" + offers +
                '}';
    }
}
