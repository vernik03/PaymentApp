package com.payment_app.webapp_oop_lab1;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CardLockServlet", value = "/CardLockServlet")
public class CardLockServlet extends HttpServlet {

    BankSystem bankSystem = new BankSystem();

    int currentCardId = 1;
    String password = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Gson gson = new Gson();
        MyData myData = gson.fromJson(sb.toString(), MyData.class);
        String possiblePassword = String.format(request.getParameter("data"));
        System.out.println("possiblePassword: " + possiblePassword);
        if (possiblePassword.equals(password)){
            List<CreditCard> creditCards = bankSystem.getAllCreditCards();
            CreditCard creditCard = creditCards.get(currentCardId - 1);
            List<BankAccount> bankAccounts = bankSystem.getAllBankAccounts();
            BankAccount bankAccount = bankAccounts.stream().filter(ba -> ba.getAccountId() == creditCard.getAccountId()).findFirst().get();
            bankAccount.unblockAccount();
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            System.out.println("Correct password");
            response.getWriter().write("Correct password");
        }
        else {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            System.out.println("Incorrect password");
            response.getWriter().write("Incorrect password");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        int cardId = Integer.parseInt(myValue);
        currentCardId = cardId;
        List<CreditCard> creditCards = bankSystem.getAllCreditCards();
        CreditCard creditCard = creditCards.get(cardId - 1);
        List<BankAccount> bankAccounts = bankSystem.getAllBankAccounts();
        BankAccount bankAccount = bankAccounts.stream().filter(ba -> ba.getAccountId() == creditCard.getAccountId()).findFirst().get();
        if (bankAccount.isBlocked()) {
//            bankAccount.unblockAccount();
//            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//            System.out.println("Unblocked successfully");
//            response.getWriter().write("Unblocked successfully");
            System.out.println("Enter password to unblock");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            response.getWriter().write("Enter password to unblock");
        } else {
            bankAccount.blockAccount();
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            System.out.println("Blocked successfully");
            response.getWriter().write("Blocked successfully");
        }
    }
}
