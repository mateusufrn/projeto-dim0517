package br.ufrn.banco.model;

public class Account {

    private static final double MAX_NEGATIVE_BALANCE = -1000.00;

    private final int number;
    private final boolean bonusAccount;
    private final boolean savingsAccount;
    private double balance;
    private int bonusPoints;

    public Account(int number) {
        this(number, false, false, 0.0);
    }

    public Account(int number, boolean bonusAccount, boolean savingsAccount) {
        this(number, bonusAccount, savingsAccount, 0.0);
    }

    public Account(int number, boolean bonusAccount, boolean savingsAccount, double initialBalance) {
        this.number = number;
        this.bonusAccount = bonusAccount;
        this.savingsAccount = savingsAccount;
        this.balance = initialBalance;
        this.bonusPoints = bonusAccount ? 10 : 0;
    }

    public int getNumber() {
        return number;
    }

    public boolean isBonusAccount() {
        return bonusAccount;
    }

    public boolean isSavingsAccount() {
        return savingsAccount;
    }

    public double getBalance() {
        return balance;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public boolean deposit(double value) {
        if (value <= 0) {
            return false;
        }

        this.balance += value;
        return true;
    }

    public void receiveTransfer(double value) {
        this.balance += value;
    }

    public void addBonusPoints(int points) {
        if (points > 0) {
            this.bonusPoints += points;
        }
    }

    public boolean withdraw(double value) {
        if (value <= 0) {
            return false;
        }

        double newBalance = this.balance - value;

        if ((bonusAccount || !savingsAccount) && newBalance < MAX_NEGATIVE_BALANCE) {
            return false;
        }

        if (savingsAccount && newBalance < 0) {
            return false;
        }

        this.balance = newBalance;
        return true;
    }

    public void applyInterest(double interestRate) {
        if (savingsAccount && interestRate > 0) {
            double interest = balance * (interestRate / 100);
            deposit(interest);
        }
    }
}