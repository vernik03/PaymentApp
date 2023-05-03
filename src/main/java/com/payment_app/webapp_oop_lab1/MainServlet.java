package com.payment_app.webapp_oop_lab1;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "MainServlet", value = "/MainServlet")
public class MainServlet extends HttpServlet {
    public int CardIdGlobal = 1;
    BankSystem bankSystem = new BankSystem();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CreditCard> creditCards = bankSystem.getAllCreditCards();
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Gson gson = new Gson();
        MyData myData = gson.fromJson(sb.toString(), MyData.class);
        int cardId = Integer.parseInt(request.getParameter("data"));
        CardIdGlobal = cardId;
        CreditCard creditCard = creditCards.get(cardId - 1);
        List<BankAccount> bankAccounts = bankSystem.getAllBankAccounts();
        //get bank account by account id. Every credit card has an account id (like 798400)
        BankAccount bankAccount = bankAccounts.stream().filter(ba -> ba.getAccountId() == creditCard.getAccountId()).findFirst().get();

        double balance =  bankAccount.getBalance();

        System.out.println("cardId: " + cardId);
        System.out.println("cardNumber: " + creditCard.getCardNumber());
        System.out.println("balance: " + balance);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cardNumber", creditCard.getCardNumber());
        jsonObject.put("balance", balance);
        jsonObject.put("isBlocked", bankAccount.isBlocked());
        response.setContentType("application/json");
        response.getWriter().write(jsonObject.toString());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String data = sb.toString();
        Gson gson = new Gson();
        MyData myData = gson.fromJson(data, MyData.class);
        String myValue = myData.getData();
        double myDoubleValue = Double.parseDouble(myValue);
        System.out.println(myDoubleValue);

        List<CreditCard> creditCards = bankSystem.getAllCreditCards();
        CreditCard creditCard = creditCards.get(CardIdGlobal - 1);
        List<BankAccount> bankAccounts = bankSystem.getAllBankAccounts();
        BankAccount bankAccount = bankAccounts.stream().filter(ba -> ba.getAccountId() == creditCard.getAccountId()).findFirst().get();
        double balance =  bankAccount.getBalance();

        if (bankAccount.isBlocked()){
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            response.getWriter().write("Your account is blocked");
        } else if (myDoubleValue < 0 && balance < Math.abs(myDoubleValue)){
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            response.getWriter().write("Not enough money on your account");
        } else {
            bankAccount.setBalance(balance + myDoubleValue);
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            if (myDoubleValue < 0){
                response.getWriter().write("Payment was successful");
            } else {
                response.getWriter().write("Replenishment was successful");
            }
        }
    }




}

