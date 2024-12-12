package org.dci;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;

public class Layout extends JFrame{

    private static final Log log = LogFactory.getLog(Layout.class);
    // creates a new card layout and passes it to the jpanel
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    // creates a Jpanel for every possible screen
    JPanel accountScreen = new JPanel();
    JPanel createAccountScreen = new JPanel();
    JPanel loginScreen = new JPanel();

    JButton loginButton = new JButton("Login");
    JButton createAccButton = new JButton("Create Account");

    Layout() {
        accountScreen.setBackground(Color.orange);
        createAccountScreen.setBackground(Color.green);
        loginScreen.setBackground(Color.yellow);

        loginScreen.add(loginButton);
        loginScreen.add(createAccButton);

//      adds panels to mainpanel
        mainPanel.add(loginScreen, "loginScreen");
        mainPanel.add(accountScreen, "accountScreen");
        mainPanel.add(createAccountScreen, "createAccountScreen");

        createAccButton.addActionListener((e) -> {
            cardLayout.show(mainPanel, "createAccountScreen");
        });

//        this sets up the jframe
        add(mainPanel);
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Layout();
    }
}
