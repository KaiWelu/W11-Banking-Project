package org.dci;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("This is gonna be the banking app lol");
        AccReadWrite readWrite = new AccReadWrite();
        readWrite.read(new File("src/main/resources/test-data.csv"));

        ArrayList<CheckingAccount> accounts = readWrite.getAccounts();
        for(CheckingAccount account  : accounts) {
            System.out.println(account.toString());
        }
        accounts.get(0).setBalance(996.222F);
        accounts.get(1).setUserName("Julia");
        readWrite.write(new File("src/main/resources/test-data.csv"));
    }
}