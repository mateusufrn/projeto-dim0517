package br.ufrn.banco.model;

public class Account {

    private final int number;
    private double balance;

    public Account(int number) {
        this.number = number;
        this.balance = 0.0;
    }

    public int getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double value) {
        if (value <= 0) {
            return false;
        }

        this.balance += value;
        return true;
    }

    public boolean withdraw(double value) {
        if (value <= 0 || value > this.balance) {
            return false;
        }

        this.balance -= value;
        return true;
    }
}