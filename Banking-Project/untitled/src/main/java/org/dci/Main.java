package org.dci;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        File data = new File("src/main/resources/database2.csv");

        AccReadWrite readWrite = new AccReadWrite();
        readWrite.read(data);

        ArrayList<Account> accounts = readWrite.getAccounts();

        new Layout(accounts, readWrite);
//      readWrite.write(new File("src/main/resources/test-data.csv"));

    }
}