package com.company;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class db {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement stmt;
    private ResultSet rs;

    private int bankCount = 0;
    private int clients = 0;
    private int users = 0;

    private static int bankID;
    private static int clientID;
    private static int depositID;
    private static int balanceID;

    public db() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingapp",
                "root","root");
        stmt = connection.createStatement();
    }

    //client db
    public boolean passwordCheck(String username, String password) throws SQLException {

        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.user WHERE username=?");
        preparedStatement.setString(1, username);

        rs = preparedStatement.executeQuery();

        if(rs.next()) {
            String check = rs.getString("password");

            int clientId = rs.getInt("id");

            if(!isBalanceCreated(clientId))
                createBalance(clientId);

            clientID = clientId;
            depositID = isDepositChosen();

            return password.equals(check);
        }

        return false;
    }

    public void createOperation(String card, String currency, double money) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO bankingapp.bankoperations (clientId, currency, money, card, date)  VALUES(?,?,?,?,?)");
        preparedStatement.setInt(1, clientID);
        preparedStatement.setString(2, currency);
        preparedStatement.setDouble(3, money);
        preparedStatement.setString(4, card);
        preparedStatement.setString(5, date);

        preparedStatement.executeUpdate();


        balanceDeletion();
    }

    public int isDepositChosen() throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.balance WHERE clientId = ?");
        preparedStatement.setInt(1, clientID);

        rs = preparedStatement.executeQuery();

        if(rs.next())
            return rs.getInt("depositId");

        return 0;
    }

    //check if balance is created for client
    public boolean isBalanceCreated(int clientId) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.balance WHERE clientId = ?");
        preparedStatement.setInt(1, clientId);

        rs = preparedStatement.executeQuery();

        return rs.next();
    }

    //create client balance
    public void createBalance(int clientId) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT INTO bankingapp.balance (clientId, bankId, depositId, uan, eur, usd, depositDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, clientId);
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 0);
        preparedStatement.setDouble(4, 0.0);
        preparedStatement.setDouble(5, 0.0);
        preparedStatement.setDouble(6, 0.0);
        preparedStatement.setString(7, "0");

        preparedStatement.executeUpdate();
    }

    public void depositMoneyToBalance(String currency, double money) throws SQLException {
        //DATE
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        preparedStatement = connection.prepareStatement("UPDATE bankingapp.balance SET uan = ?, eur = ?, usd = ?, depositDate = ? WHERE clientId = ?");

        switch (currency) {
            case "uan":
                preparedStatement.setDouble(1, money);
                preparedStatement.setDouble(2, 0.0);
                preparedStatement.setDouble(3, 0.0);
                preparedStatement.setString(4, date);
                preparedStatement.setInt(5, clientID);
                break;
            case "eur":
                preparedStatement.setDouble(1, 0.0);
                preparedStatement.setDouble(2, money);
                preparedStatement.setDouble(3, 0.0);
                preparedStatement.setString(4, date);
                preparedStatement.setInt(5, clientID);
                break;
            case "usd":
                preparedStatement.setDouble(1, 0.0);
                preparedStatement.setDouble(2, 0.0);
                preparedStatement.setDouble(3, money);
                preparedStatement.setString(4, date);
                preparedStatement.setInt(5, clientID);
                break;
        }

        preparedStatement.executeUpdate();

    }

    public void balanceDeletion() throws SQLException {
        preparedStatement = connection.prepareStatement("UPDATE bankingapp.balance SET bankId = 0, depositId = 0, uan = 0.0, eur = 0.0, usd = 0.0, depositDate = '0' WHERE clientId = ?");
        preparedStatement.setInt(1, clientID);
        preparedStatement.executeUpdate();
        depositID = 0;
    }

    public void changeClientInfo(Client client) throws SQLException {
        preparedStatement = connection.prepareStatement("UPDATE bankingapp.client SET firstName = ?, secondName = ?, age = ?, passportId = ?, phoneNumber = ? WHERE id = ?");
        preparedStatement.setString(1, client.getFirstName());
        preparedStatement.setString(2, client.getSecondName());
        preparedStatement.setInt(3, client.getAge());
        preparedStatement.setInt(4, client.getPassportId());
        preparedStatement.setDouble(5, client.getPhoneNumber());
        preparedStatement.setInt(6, clientID);
        preparedStatement.executeUpdate();
    }

    public Client getClientById() throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.client WHERE id = ?");
        preparedStatement.setInt(1, clientID);
        rs = preparedStatement.executeQuery();
        Client client;
        if(rs.next()){
            if(depositID == 0) {
                client = new Client(rs.getString("firstName"),
                        rs.getString("secondName"),
                        rs.getInt("age"),
                        rs.getInt("passportId"),
                        rs.getDouble("phoneNumber"));
                client.setDepositOffer(null);
                client.setBalance(null);
            }
            else{
                client = new Client(rs.getString("firstName"),
                        rs.getString("secondName"),
                        rs.getInt("age"),
                        rs.getInt("passportId"),
                        rs.getDouble("phoneNumber"), getBalanceDeposit(),
                        getBalanceById());
            }

            return client;
        }

        return null;
    }

    public Balance getBalanceById() throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.balance WHERE clientId = ?");
        preparedStatement.setInt(1, clientID);
        rs = preparedStatement.executeQuery();

        if(rs.next())
            return new Balance(rs.getDouble("uan"),
                    rs.getDouble("eur"),
                    rs.getDouble("usd"),
                    rs.getString("depositDate"));

        return null;
    }

    public int insertClient(String firstName, String secondName,
                            int age, int passportId, double phoneNumber) throws SQLException {
        rs = stmt.executeQuery("SELECT id FROM bankingapp.client");

        int id_of_new_client = 0;

        while(rs.next()) {
            id_of_new_client = rs.getInt("id");
        }

        id_of_new_client++;

        preparedStatement = connection.prepareStatement("INSERT INTO bankingapp.client (id, firstName, secondName, age, passportId, phoneNumber) " +
                "VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, id_of_new_client);
        preparedStatement.setString(2, firstName);
        preparedStatement.setString(3, secondName);
        preparedStatement.setInt(4, age);
        preparedStatement.setInt(5, passportId);
        preparedStatement.setDouble(6, phoneNumber);

        preparedStatement.executeUpdate();

        return id_of_new_client;
    }

    public boolean isUsernameCorrect(String username) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.user WHERE username=?");
        preparedStatement.setString(1, username);

        rs = preparedStatement.executeQuery();

        if(rs.next()) {
            return false;
        }

        return true;
    }

    public void insertUser(int clientId, String username, String password) throws SQLException {
        rs = stmt.executeQuery("SELECT id FROM bankingapp.user");

        int id = 0;

        while(rs.next()) {
            id = rs.getInt("id");
        }

        id++;

        preparedStatement = connection.prepareStatement("INSERT INTO bankingapp.user (id, clientId, username, password) " +
                "VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, clientId);
        preparedStatement.setString(3, username);
        preparedStatement.setString(4, password);

        preparedStatement.executeUpdate();

    }

    public int getIndexOfBank() throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.balance WHERE clientId = ?");
        preparedStatement.setInt(1, clientID);

        rs = preparedStatement.executeQuery();

        if (rs.next())
            return rs.getInt("bankId");

        return 0;
    }

    public int getIndexOfBankByName(String name) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.bank WHERE bankName = ?");
        preparedStatement.setString(1, name);

        rs = preparedStatement.executeQuery();

        if (rs.next())
            return rs.getInt("id");

        return 0;
    }

    public Bank getBanks(int i) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.bank WHERE id = ?");
        preparedStatement.setInt(1, i);

        rs = preparedStatement.executeQuery();

        rs.next();

        Bank b = new Bank(rs.getString("bankName"), rs.getInt("rate"), rs.getInt("humanTrust"));

        int dep = countDepositsForBank(i);

        b.setRegularArray(dep);

        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.deposits WHERE bankId = ?");
        preparedStatement.setInt(1, i);

        rs = preparedStatement.executeQuery();

        int el = 0;
        while(rs.next()){

            b.setRegularElement(el, rs.getDouble("maxSum"),
                    rs.getInt("timeOfIncome")*30,
                    rs.getBoolean("usd"),
                    rs.getBoolean("uan"),
                    rs.getBoolean("eur"),
                    rs.getBoolean("terminationPosibillity"),
                    rs.getDouble("percent"));
            el++;
        }

        return b;
    }

    public int countDepositsForBank(int i) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.deposits WHERE bankId = ?");
        preparedStatement.setInt(1, i);

        rs = preparedStatement.executeQuery();

        int dep = 0;

        while(rs.next()){
            dep++;
        }

        return dep;
    }

    public int countBanks() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM bankingapp.bank");

        while(rs.next()){
            bankCount++;
        }

        return bankCount;
    }

    public int countOperation() throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.bankoperations WHERE clientid = ?");
        preparedStatement.setInt(1, clientID);

        rs = preparedStatement.executeQuery();

        int operations = 0;
        while(rs.next()){
            operations++;
        }

        return operations;
    }

    public BankOperation[] getBankOperations(int n) throws SQLException {
        BankOperation[] bankOperations = new BankOperation[n];
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.bankoperations WHERE clientid = ?");
        preparedStatement.setInt(1, clientID);

        rs = preparedStatement.executeQuery();

        int current = 0;
        while(rs.next()){
            bankOperations[current] = new BankOperation(rs.getDouble("money"),
                    rs.getString("currency"),
                    rs.getString("card"),
                    rs.getString("date"));
            current++;
        }

        return bankOperations;
    }



    public void setBankIdForBalance(int bankId) throws SQLException {
        preparedStatement = connection.prepareStatement("UPDATE bankingapp.balance SET bankId = ? WHERE clientId = ?");
        preparedStatement.setInt(1, bankId);
        preparedStatement.setInt(2, clientID);

        preparedStatement.executeUpdate();

        bankID = bankId;
    }

    public void setDepositIdForBalance(int depositId) throws SQLException {
        preparedStatement = connection.prepareStatement("UPDATE bankingapp.balance SET depositId = ? WHERE clientId = ?");
        preparedStatement.setInt(1, depositId);
        preparedStatement.setInt(2, clientID);

        preparedStatement.executeUpdate();

        depositID = depositId;
    }

    public DepositOffer getBalanceDeposit() throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.deposits WHERE id = ?");
        preparedStatement.setInt(1, depositID);

        rs = preparedStatement.executeQuery();

        if(rs.next()){
            return new DepositOffer(rs.getDouble("maxSum"),
                    rs.getInt("timeOfIncome")*30,
                    rs.getBoolean("usd"),
                    rs.getBoolean("uan"),
                    rs.getBoolean("eur"),
                    rs.getBoolean("terminationPosibillity"),
                    rs.getDouble("percent"));
        }

        return null;
    }



    public int getIndexOfDepositByProperties(DepositOffer d) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM bankingapp.deposits WHERE bankId = ?");
        preparedStatement.setInt(1, bankID);

        rs = preparedStatement.executeQuery();

        int index = 0;

        while(rs.next()){
            if (rs.getDouble("maxSum") == d.getMaxSum() &&
                    rs.getDouble("timeOfIncome") * 30 == d.getMaxDepositTime() &&
                    rs.getBoolean("usd") == d.isUsd() &&
                    rs.getBoolean("uan") == d.isUah() &&
                    rs.getBoolean("eur") == d.isEur() &&
                    rs.getBoolean("terminationPosibillity") == d.isTerminationPossibility() &&
                    rs.getDouble("percent") == d.getGrowthPercentage()) {
                index = rs.getInt("id");
                break;
            }
        }

        return index;
    }
}
