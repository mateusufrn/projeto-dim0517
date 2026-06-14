package br.ufrn.banco.model;

public class Account {

    private final int number;
    private final boolean bonusAccount;
    private final boolean savingsAccount;
    private double balance;
    private int bonusPoints;

    public Account(int number, double initialBalance, boolean bonusAccount, boolean savingsAccount) {
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

    public void deposit(double value) {
        this.balance += value;
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
        if (value <= 0 || value > this.balance) {
            return false;
        }

        this.balance -= value;
        return true;
    }

    public void applyInterest(double interestRate) {
        if (savingsAccount && interestRate > 0) {
            double interest = balance * (interestRate / 100);
            deposit(interest);
        }
    }
}
