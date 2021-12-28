package com.company;

public class DepositOffer {
    private double maxSum;
    private int maxDepositTime; //час після якого можна зарахувати гроші
    //currencies
    private boolean usd;
    private boolean uah;
    private boolean eur;
    //
    private boolean terminationPossibility;
    private double growthPercentage;
    //

    public DepositOffer() {
        this.maxSum = 0;
        this.maxDepositTime = 0;
        this.usd = false;
        this.uah = false;
        this.eur = false;
        this.terminationPossibility = true;
        this.growthPercentage = 0;
    }

    public DepositOffer(double maxSum, int maxDepositTime, boolean usd,
                       boolean uah, boolean eur, boolean terminationPossibility, double growthPercentage) {
        this.maxSum = maxSum;
        this.maxDepositTime = maxDepositTime;
        this.usd = usd;
        this.uah = uah;
        this.eur = eur;
        this.terminationPossibility = terminationPossibility;
        this.growthPercentage = growthPercentage;
    }

    public DepositOffer(DepositOffer deposit){
        this.maxSum = deposit.maxSum;
        this.maxDepositTime = deposit.maxDepositTime;
        this.usd = deposit.usd;
        this.uah = deposit.uah;
        this.eur = deposit.eur;
        this.terminationPossibility = deposit.terminationPossibility;
        this.growthPercentage = deposit.growthPercentage;
    }

    public double getMaxSum() {
        return maxSum;
    }

    public void setMaxSum(int maxSum) {
        this.maxSum = maxSum;
    }

    public int getMaxDepositTime() {
        return maxDepositTime;
    }

    public void setMaxDepositTime(int maxDepositTime) {
        this.maxDepositTime = maxDepositTime;
    }

    public boolean isUsd() {
        return usd;
    }

    public void setUsd(boolean usd) {
        this.usd = usd;
    }

    public boolean isUah() {
        return uah;
    }

    public void setUah(boolean uah) {
        this.uah = uah;
    }

    public boolean isEur() {
        return eur;
    }

    public void setEur(boolean eur) {
        this.eur = eur;
    }

    public boolean isTerminationPossibility() {
        return terminationPossibility;
    }

    public void setTerminationPossibility(boolean terminationPossibility) {
        this.terminationPossibility = terminationPossibility;
    }

    public double getGrowthPercentage() {
        return growthPercentage;
    }

    public void setGrowthPercentage(int growthPercentage) {
        this.growthPercentage = growthPercentage;
    }

    @Override
    public String toString() {
        return "DepositOffer{" +
                "maxSum=" + maxSum +
                ", maxDepositTime=" + maxDepositTime +
                ", usd=" + usd +
                ", uah=" + uah +
                ", eur=" + eur +
                ", terminationPossibility=" + terminationPossibility +
                ", growthPercentage=" + growthPercentage +
                '}';
    }
}
