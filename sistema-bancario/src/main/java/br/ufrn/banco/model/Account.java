package br.ufrn.banco.model;

public class Account {

    private final int number;
    private final boolean bonusAccount;
    private double balance;
    private int bonusPoints;

    public Account(int number) {
        this(number, false);
    }

    public Account(int number, boolean bonusAccount) {
        this.number = number;
        this.bonusAccount = bonusAccount;
        this.balance = 0.0;
        this.bonusPoints = bonusAccount ? 10 : 0;
    }

    public int getNumber() {
        return number;
    }

    public boolean isBonusAccount() {
        return bonusAccount;
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
        this.balance -= value;
        return true;
    }
}
