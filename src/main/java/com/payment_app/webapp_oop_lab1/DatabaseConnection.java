package com.payment_app.webapp_oop_lab1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {
    private static final String DB_NAME = "PaymentDB";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_HOST = "85.209.46.216";
    private static final int DB_PORT = 5432; // порт базы данных

    private Connection connection;

    public DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME,
                    DB_USER, DB_PASSWORD);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS CreditCards (id SERIAL PRIMARY KEY, card_number VARCHAR(20) NOT NULL, account_id INT NOT NULL);");
        statement.execute("CREATE TABLE IF NOT EXISTS BankAccounts (id SERIAL PRIMARY KEY, account_id INT NOT NULL, balance DECIMAL(10,2) NOT NULL, is_blocked BOOLEAN NOT NULL);");
        statement.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
