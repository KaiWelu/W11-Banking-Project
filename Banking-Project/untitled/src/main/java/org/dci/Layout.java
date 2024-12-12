package org.dci;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Layout extends JFrame{
    public ArrayList<Account> session;
    public Account user;

    // creates a new card layout and passes it to the jpanel
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    // creates a Jpanel for every possible screen
    JPanel accountScreen = new JPanel();
    JPanel createAccountScreen = new JPanel();
    JPanel loginScreen = new JPanel();


    Layout(ArrayList<Account> accounts) {
        ArrayList<Account> session = accounts;
        Account user = session.get(0);

        JButton loginButton = new JButton("Login");
        JButton createAccButton = new JButton("Create Account");

//      sets background and borders (invisible for margins) etc.
        accountScreen.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        accountScreen.setBackground(Color.orange);
        createAccountScreen.setBackground(Color.green);
        loginScreen.setBackground(Color.yellow);

        loginScreen.add(loginButton);
        loginScreen.add(createAccButton);

//      adds panels to mainpanel
        mainPanel.add(loginScreen, "loginScreen");
        mainPanel.add(accountScreen, "accountScreen");
        mainPanel.add(createAccountScreen, "createAccountScreen");

//        createAccButton.addActionListener((e) -> {
//            cardLayout.show(mainPanel, "accountScreen");
//        });

//        layout for different screens
        accountScreen.setLayout(new BoxLayout(accountScreen, BoxLayout.Y_AXIS));

//        text fields for account screen
        JLabel userLabel = new JLabel("Username: " + user.getUserName());
        accountScreen.add(userLabel);

        JLabel idLabel = new JLabel("ID: " + String.valueOf(user.getId()));
        accountScreen.add(idLabel);

        JLabel balanceLabel = new JLabel("Balance: " + String.valueOf(user.getBalance()) + " $");
        accountScreen.add(balanceLabel);

        JLabel limitLabel = new JLabel("Withdraw Limit: " + String.valueOf(user.getWithdrawLimit()));
        accountScreen.add(limitLabel);



        //        text fields for login screen
        JTextField userNameField = new JTextField(10);
        userNameField.setText("Username");
        loginScreen.add(userNameField);

        JTextField passwordField = new JTextField(10);
        passwordField.setText("Password");
        loginScreen.add(passwordField);


        loginButton.addActionListener((e) -> {
            for(Account account : session) {
                if(account.getUserName().equals(userNameField.getText()) && account.getPassword().equals(passwordField.getText()) ) {
                    setUser(account);
                    cardLayout.show(mainPanel, "accountScreen");
                } else {
                    System.out.println(userNameField.getText());
                    System.out.println(passwordField.getText());
                    System.out.println(account.getUserName());
                    System.out.println(account.getPassword());
                }
            }
            System.out.println("Something went wrong!");
        });

        //        this sets up the jframe
        add(mainPanel);
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {

//        new Layout(account);
    }

    public void setUser(Account account) {
        user = account;
    }
}
