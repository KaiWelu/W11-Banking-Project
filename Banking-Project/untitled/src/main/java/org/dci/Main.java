package org.dci;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("This is gonna be the banking app lol");
        AccReadWrite readWrite = new AccReadWrite();
        readWrite.read(new File("src/main/resources/test-data.csv"));

        ArrayList<Account> accounts = readWrite.getAccounts();

        new Layout(accounts);
        readWrite.write(new File("src/main/resources/test-data.csv"));

    }
}