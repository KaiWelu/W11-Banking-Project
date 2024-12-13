package org.dci;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Layout extends JFrame{
//    public ArrayList<Account> session;
    public Account user;
    public AccReadWrite reWr;

    // creates a new card layout and passes it to the jpanel
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    // creates a Jpanel for every possible screen
    JPanel accountScreen = new JPanel();
    JPanel createAccountScreen = new JPanel();
    JPanel loginScreen = new JPanel();


    Layout(ArrayList<Account> accounts, AccReadWrite readWrite) {
        Account user = null;
        ArrayList<Account> userAccounts = accounts;
        this.reWr = readWrite;


        JButton loginButton = new JButton("Login");
        JButton createAccButton = new JButton("Create Account");

//      sets background and borders (invisible for margins) etc.
        accountScreen.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        accountScreen.setBackground(Color.orange);
        createAccountScreen.setBackground(Color.green);
        loginScreen.setBackground(Color.yellow);



//      adds panels to mainpanel
        mainPanel.add(loginScreen, "loginScreen");
        mainPanel.add(accountScreen, "accountScreen");
        mainPanel.add(createAccountScreen, "createAccountScreen");


//        layout for different screens
        accountScreen.setLayout(new BoxLayout(accountScreen, BoxLayout.Y_AXIS));
        // createAccountScreen.setLayout(new BoxLayout(createAccountScreen, BoxLayout.Y_AXIS));

        // text fields and buttons for create acc screen
        JLabel createAccLabel = new JLabel("Create a new bank account");
        createAccountScreen.add(createAccLabel);

        JTextField createUserField = new JTextField("Username", 20);
        createAccountScreen.add(createUserField);

        JTextField createPasswordField = new JTextField("Password", 20);
        createAccountScreen.add(createPasswordField);

        JTextField createPinField = new JTextField("PIN", 20);
        createAccountScreen.add(createPinField);

        JButton newAccountButton = new JButton("Create Account");
        createAccountScreen.add(newAccountButton);

        newAccountButton.addActionListener((e) -> {
            try {
                createNewAccount(createUserField.getText(), createPasswordField.getText(), createPinField.getText(), accounts, readWrite);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton toLoginButton = new JButton("Back");
        createAccountScreen.add(toLoginButton);

        toLoginButton.addActionListener((e) -> {
            cardLayout.show(mainPanel, "loginScreen");
        });


        //  text fields for login screen and adds buttons
        JTextField userNameField = new JTextField(10);
        userNameField.setText("Username");
        loginScreen.add(userNameField);

        JTextField passwordField = new JTextField(10);
        passwordField.setText("Password");
        loginScreen.add(passwordField);

        loginScreen.add(loginButton);
        loginScreen.add(createAccButton);

        createAccButton.addActionListener((e) -> {
            cardLayout.show(mainPanel, "createAccountScreen");
        });


        loginButton.addActionListener((e) -> {
            for(Account account : accounts) {
                if(account.getUserName().equals(userNameField.getText()) && account.getPassword().equals(passwordField.getText()) ) {
                    setUser(account);
                    System.out.println(account.getUserName());
                    cardLayout.show(mainPanel, "accountScreen");
                    return;
                }
            }
            JOptionPane.showMessageDialog(loginScreen, "Username or Password is wrong!", "Error", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Something went wrong!");
        });



//        deposit


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

        //        text fields for account screen
        JLabel userLabel = new JLabel("Username: " + user.getUserName());
        accountScreen.add(userLabel);

        JLabel idLabel = new JLabel("ID: " + String.valueOf(user.getId()));
        accountScreen.add(idLabel);

        JLabel balanceLabel = new JLabel("Balance: " + String.valueOf(user.getBalance()) + " $");
        accountScreen.add(balanceLabel);

        JLabel limitLabel = new JLabel("Withdraw Limit: " + String.valueOf(user.getWithdrawLimit()) + " $");
        accountScreen.add(limitLabel);

        //        withdraw dialogue and button
        JButton withdrawButton = new JButton("Withdraw");
        accountScreen.add(withdrawButton);

        withdrawButton.addActionListener((e) -> {
            try {
                withDrawDialogue(balanceLabel);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    public void createNewAccount(String name, String password, String pin, ArrayList<Account> accounts, AccReadWrite readWrite) throws Exception {
        accounts.add(new Account(name, password, 0, Integer.parseInt(pin), accounts.size()+1, "silver", true, true, 50.00f ));
        readWrite.write(new File("src/main/resources/database2.csv"));
        System.out.println(accounts.getLast().toString());
    }

    public void withDrawDialogue(JLabel balanceLabel) throws Exception {
        JTextField amount = new JTextField(5);
        JTextField pin = new JTextField(5);

        JPanel withDrawPanel = new JPanel();
        withDrawPanel.add(new JLabel("Amount: "));
        withDrawPanel.add(amount);
        withDrawPanel.add(new JLabel("PIN: "));
        withDrawPanel.add(pin);

        int result = JOptionPane.showConfirmDialog(accountScreen, withDrawPanel, "Withdraw Money", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION) {
           boolean withDrawResult = user.withDrawMoney(Float.parseFloat(amount.getText()), Integer.parseInt(pin.getText()));
           if(!withDrawResult) {
               JOptionPane.showMessageDialog(loginScreen, "Incorrect inputs or insufficient funds!", "Error", JOptionPane.INFORMATION_MESSAGE);
           } else {
               reWr.write(new File("src/main/resources/database2.csv"));
               System.out.println(user.getBalance());
               balanceLabel.setText("Balance: " + String.valueOf(user.getBalance()) + " $");
           }
        }
    }
}
