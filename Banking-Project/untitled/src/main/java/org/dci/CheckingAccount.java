package org.dci;

public class CheckingAccount extends Account {


    public CheckingAccount(String userName, String password, float balance, int pin, int id, String typeOfAccount, boolean isCheckingAccount, boolean isActive, float withdrawLimit) {
        super(userName, password, balance, pin, id, typeOfAccount, isCheckingAccount, isActive, withdrawLimit);
    }
}
