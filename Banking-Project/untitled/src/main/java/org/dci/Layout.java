package org.dci;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Layout extends JFrame{
//    public ArrayList<Account> session;
    public Account user;
    public AccReadWrite reWr;
    public ArrayList<Account> userAccounts;

    // creates a new card layout and passes it to the jpanel
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    // creates a Jpanel for every possible screen
    JPanel accountScreen = new JPanel();
    JPanel createAccountScreen = new JPanel();
    JPanel loginScreen = new JPanel();


    Layout(ArrayList<Account> accounts, AccReadWrite readWrite) {
        Account user = null;
        this.userAccounts = accounts;
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

        // text fields and buttons for create acc screen
        //JLabel createAccLabel = new JLabel("Create a new bank account");
        //createAccountScreen.add(createAccLabel);

        createAccountScreen.add(new JLabel("Username"));
        JTextField createUserField = new JTextField("Username", 20);
        createAccountScreen.add(createUserField);

        createAccountScreen.add(new JLabel("Password"));
        JPasswordField createPasswordField = new JPasswordField("Password", 20);
        createAccountScreen.add(createPasswordField);

        createAccountScreen.add(new JLabel("PIN"));
        JPasswordField createPinField = new JPasswordField("PIN", 20);
        createAccountScreen.add(createPinField);

        JButton newAccountButton = new JButton("Create Account");
        createAccountScreen.add(newAccountButton);

        newAccountButton.addActionListener((e) -> {
            try {
                createNewAccount(createUserField.getText(), createPasswordField.getText(), createPinField.getText(), accounts, readWrite);
                createUserField.setText("Username");
                createPasswordField.setText("Password");
                createPinField.setText("PIN");
                setUser(accounts.getLast());
                cardLayout.show(mainPanel, "accountScreen");
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

        JTextField passwordField = new JPasswordField(10);
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
                    cardLayout.show(mainPanel, "accountScreen");
                    return;
                }
            }
            JOptionPane.showMessageDialog(loginScreen, "Username or Password is wrong!", "Error", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Something went wrong!");
        });
        // this sets up the jframe
        add(mainPanel);
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

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

        //deposit and withdraw dialogue
        JButton withdrawButton = new JButton("Withdraw");
        accountScreen.add(withdrawButton);

        JButton depositButton = new JButton("Deposit");
        accountScreen.add(depositButton);

        withdrawButton.addActionListener((e) -> {
            try {
                withDrawDialogue(balanceLabel);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        depositButton.addActionListener((e) -> {
            try {
                depositDialogue(balanceLabel);
            } catch (Exception ex) {
                throw  new RuntimeException(ex);
            }
        });

        // send money dialogue
        JButton sendMoneyButton = new JButton("Send Money");
        accountScreen.add(sendMoneyButton);

        sendMoneyButton.addActionListener((e) -> {
            try {
                sendMoney(balanceLabel);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton changePasswordButton = new JButton("Change Password");
        accountScreen.add(changePasswordButton);

        changePasswordButton.addActionListener((e) -> {
            try {
                changePassword(user);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton logoutButton = new JButton("Logout");
        accountScreen.add(logoutButton);

        logoutButton.addActionListener((e) -> {
            cardLayout.show(mainPanel, "loginScreen");
            // removes all components from the previous user
            accountScreen.removeAll();
        });



    }

    public void createNewAccount(String name, String password, String pin, ArrayList<Account> accounts, AccReadWrite readWrite) throws Exception {
        accounts.add(new Account(name, password, 0, Integer.parseInt(pin), accounts.size()+1, "silver", true, true, 50.00f ));

        readWrite.write();

    }

    public void changePassword(Account user) throws  Exception {
        JPanel changePasswordPanel = new JPanel();
        JPasswordField  oldPassword = new JPasswordField(5);
        JPasswordField  newPassword = new JPasswordField(5);

        changePasswordPanel.add(new JLabel("Old Password"));
        changePasswordPanel.add(oldPassword);

        changePasswordPanel.add(new JLabel("New Password"));
        changePasswordPanel.add(newPassword);

        int result = JOptionPane.showConfirmDialog(accountScreen, changePasswordPanel, "Change Password", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION) {
            if(!oldPassword.getText().equals(user.getPassword())) {
                JOptionPane.showMessageDialog(loginScreen, "Incorrect password!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
                user.setPassword(newPassword.getText());
                reWr.write();
        }

    }

    public void withDrawDialogue(JLabel balanceLabel) throws Exception {
        JTextField amount = new JTextField(5);
        JPasswordField pin = new JPasswordField(5);

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
               reWr.write();
               System.out.println(user.getBalance());
               balanceLabel.setText("Balance: " + String.valueOf(user.getBalance()) + " $");
           }
        }
    }

    public void depositDialogue(JLabel balanceLabel) throws Exception {
        JTextField amount = new JTextField(5);
        JPasswordField pin = new JPasswordField(5);

        JPanel depositPanel = new JPanel();
        depositPanel.add(new JLabel("Amount: "));
        depositPanel.add(amount);
        depositPanel.add(new JLabel("PIN: "));
        depositPanel.add(pin);

        int result = JOptionPane.showConfirmDialog(accountScreen, depositPanel, "Deposit Money", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION) {
            boolean depositResult = user.depositMoney(Float.parseFloat(amount.getText()), Integer.parseInt(pin.getText()));
            if(!depositResult) {
                JOptionPane.showMessageDialog(loginScreen, "Incorrect inputs!", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                reWr.write();
                balanceLabel.setText("Balance: " + String.valueOf(user.getBalance()) + " $");
            }
        }
    }

    public void sendMoney(JLabel balanceLabel) throws Exception {
        JTextField amount = new JTextField(5);
        JTextField pin = new JPasswordField(5);
        JTextField targetUser = new JTextField(5);

        JPanel sendPanel = new JPanel();
        sendPanel.add(new JLabel("Amount: "));
        sendPanel.add(amount);
        sendPanel.add(new JLabel("PIN: "));
        sendPanel.add(pin);
        sendPanel.add(new JLabel("Target User"));
        sendPanel.add(targetUser);

        int result = JOptionPane.showConfirmDialog(accountScreen, sendPanel, "Deposit Money", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION) {
            // search for target user
            Account target = null;
            for(Account account : userAccounts) {
                if(account.getUserName().equals(targetUser.getText())) {
                    target = account;
                }
            }

            // check if target was found
            if(target == null) {
                JOptionPane.showMessageDialog(loginScreen, "Incorrect inputs!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

            boolean withDrawResult = user.withDrawMoney(Float.parseFloat(amount.getText()), Integer.parseInt(pin.getText()));
            boolean depositResult = target.depositMoney(Float.parseFloat(amount.getText()), target.getPin());
            if(!withDrawResult || !depositResult) {
                JOptionPane.showMessageDialog(loginScreen, "Incorrect inputs!", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                reWr.write();
                balanceLabel.setText("Balance: " + String.valueOf(user.getBalance()) + " $");
            }
        }
    }
}
