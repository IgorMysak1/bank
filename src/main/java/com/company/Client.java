package com.company;

public class Client {
    private String firstName;
    private String secondName;
    private int age;
    private int passportId;
    private double phoneNumber;
    private DepositOffer depositOffer;
    private Balance balance;

    public Client(String firstName, String secondName, int age, int passportId, double phoneNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.passportId = passportId;
        this.phoneNumber = phoneNumber;
    }

    public Client(String firstName, String secondName, int age, int passportId, double phoneNumber, DepositOffer depositOffer, Balance balance) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.passportId = passportId;
        this.phoneNumber = phoneNumber;
        this.depositOffer = new DepositOffer(depositOffer);
        this.balance = new Balance(balance);
    }

    public Client(Client client){
        this.firstName = client.firstName;
        this.secondName = client.secondName;
        this.age = client.age;
        this.passportId = client.passportId;
        this.phoneNumber = client.phoneNumber;
        if (client.depositOffer != null)
            this.depositOffer = new DepositOffer(client.depositOffer);
        else this.depositOffer = null;
        if (client.balance != null)
            this.balance = new Balance(client.balance);
        else this.balance = null;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public DepositOffer getDepositOffer() {
        return depositOffer;
    }

    public void setDepositOffer(DepositOffer depositOffer) {
        this.depositOffer = depositOffer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public double getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(double phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
