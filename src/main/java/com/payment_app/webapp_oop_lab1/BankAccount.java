package com.payment_app.webapp_oop_lab1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankAccount {
    private int accountId;
    private double balance;
    private boolean isBlocked;

    public BankAccount(int accountId, double balance, boolean isBlocked) {
        this.accountId = accountId;
        this.balance = balance;
        this.isBlocked = isBlocked;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void blockAccount() {
        isBlocked = true;
        changeBlockStatus();
    }

    public void unblockAccount() {
        isBlocked = false;
        changeBlockStatus();
    }

    private void changeBlockStatus() {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement("UPDATE bankaccounts SET is_blocked = ? WHERE account_id = ?;");
            statement.setBoolean(1, isBlocked ? true : false);
            statement.setInt(2, accountId);
            statement.executeUpdate();

            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBalance(double amount) {
        if (!isBlocked){
            balance += amount;
        }
    }

    public boolean deductBalance(double amount) {
        if (isBlocked) {
            return false;
        }
        else if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public void setBalance(double balance) {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement("UPDATE bankaccounts SET balance = ? WHERE account_id = ?;");
            statement.setDouble(1, balance);
            statement.setInt(2, accountId);
            statement.executeUpdate();

            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
