package com.payment_app.webapp_oop_lab1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankSystem {

    public List<CreditCard> getAllCreditCards() {
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            // Connect to the database
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // Execute a query to retrieve all credit cards
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM CreditCards;");
            ResultSet resultSet = statement.executeQuery();

            // Loop through the result set and create credit card objects
            while (resultSet.next()) {
                String cardNumber = resultSet.getString("card_number");
                int accountId = resultSet.getInt("account_id");
                CreditCard creditCard = new CreditCard(cardNumber, accountId);
                creditCards.add(creditCard);
            }

            // Close the database connection
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creditCards;
    }

    public List<BankAccount> getAllBankAccounts(){
        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            // Connect to the database
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // Execute a query to retrieve all credit cards
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bankaccounts;");
            ResultSet resultSet = statement.executeQuery();

            // Loop through the result set and create credit card objects
            while (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                double balance = resultSet.getDouble("balance");
                boolean isBlocked = resultSet.getBoolean("is_blocked");

                BankAccount bankAccount = new BankAccount(accountId, balance, isBlocked);
                bankAccounts.add(bankAccount);
            }

            // Close the database connection
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankAccounts;
    }
}
