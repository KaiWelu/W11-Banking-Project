package org.dci;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        // sets up a file to store the account data
        File file = new File("src/main/resources/database2.csv");
        // sets up a csv reader class object to read and write data
        AccReadWrite readWrite = new AccReadWrite(file);

        //readWrite.read();
        // gets the accounts from the csv reader (refactor this)
        ArrayList<Account> accounts = readWrite.getAccounts();

        // creates a new layout and passes accounts and reader as arguments
        new Layout(accounts, readWrite);
    }
}