package org.dci;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("This is gonna be the banking app lol");
        AccReadWrite readWrite = new AccReadWrite();
        readWrite.read(new File("src/main/resources/test-data.csv"));

        ArrayList<Account> accounts = readWrite.getAccounts();
        for(Account account  : accounts) {
            System.out.println(account.toString());
        }
        accounts.add(new Account("Heiner", "rofl", 89.00f, 6666, 3, "gold", false, true, -120.00f));
        accounts.get(0).setBalance(996.222F);
        accounts.get(1).setUserName("Julia");
        readWrite.write(new File("src/main/resources/test-data.csv"));

        JFrame frame = new JFrame("Banking App");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JPanel panel = new JPanel();
        panel.setBackground(Color.orange);
        JButton okButton = new JButton("Hallo");
        panel.add(okButton);
        frame.add(panel);
    }
}