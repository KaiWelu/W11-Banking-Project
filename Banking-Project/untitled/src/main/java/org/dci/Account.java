package org.dci;

public class Account {
    private String userName;
    private String password;
    private float balance;
    private int pin;
    private int id;
    private String typeOfAccount;
    private boolean isCheckingAccount;
    private boolean isActive;
    private float withdrawLimit;

    public Account(String userName, String password, float balance, int pin, int id, String typeOfAccount, boolean isCheckingAccount, boolean isActive, float withdrawLimit) {
        this.userName = userName;
        this.password = password;
        this.balance = balance;
        this.pin = pin;
        this.id = id;
        this.typeOfAccount = typeOfAccount;
        this.isCheckingAccount = isCheckingAccount;
        this.isActive = isActive;
        this.withdrawLimit = withdrawLimit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public boolean isCheckingAccount() {
        return isCheckingAccount;
    }

    public void setCheckingAccount(boolean checkingAccount) {
        isCheckingAccount = checkingAccount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public float getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(float withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public boolean withDrawMoney(float amount, int inputPin) {
        if(pin != inputPin) {
            return false;
        }
        if((balance - amount) < (withdrawLimit*-1)) {
            return false;
        }

        balance = balance - amount;
        return true;
    }

    public boolean depositMoney(float amount, int inputPin) {
        if(pin != inputPin) {
            return false;
        }
        balance = balance + amount;
        return true;
    }

    @Override
    public String toString() {
        return "Username: " + getUserName() + " Balance: " + getBalance();
    }
}
