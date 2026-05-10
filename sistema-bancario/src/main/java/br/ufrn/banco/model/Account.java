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

    public void deposit(double valor) {
        this.balance += valor;
    }

    public boolean withdraw(double value) {
        this.balance -= value;
        return true;
    }
}