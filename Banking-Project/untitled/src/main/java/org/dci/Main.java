package org.dci;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("This is gonna be the banking app lol");
        AccountsReader reader = new AccountsReader();
        reader.read(new File("src/main/resources/test-data.csv"));
    }
}