package br.ufrn.banco.service;

import br.ufrn.banco.model.Account;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final List<Account> accounts = new ArrayList<>();

    public Account registerAccount(int number) {
        return registerAccount(number, false, false);
    }

    public Account registerBonusAccount(int number) {
        return registerAccount(number, true, false);
    }

    public Account registerSavingsAccount(int number) {
        return registerAccount(number, false, true);
    }

    public Account registerAccount(int number, boolean isBonusAccount, boolean isSavingsAccount) {
        Account existentAccount = searchAccount(number);

        if (existentAccount != null) {
            return null;
        }

        Account newAccount = new Account(number, isBonusAccount, isSavingsAccount);
        accounts.add(newAccount);
        return newAccount;
    }

    public Account searchAccount(int number) {
        for (Account account : accounts) {
            if (account.getNumber() == number) {
                return account;
            }
        }

        return null;
    }

    public boolean deposit(int number, double value) {
        Account account = searchAccount(number);

        if (account == null || value <= 0) {
            return false;
        }

        account.deposit(value);

        if (account.isBonusAccount()) {
            account.addBonusPoints(calculateDepositBonusPoints(value));
        }

        return true;
    }

    public boolean withdraw(int number, double value) {
        Account account = searchAccount(number);

        if (account == null) {
            return false;
        }

        return account.withdraw(value);
    }

    public boolean transfer(int sourceNumber, int destinationNumber, double value) {
        if (value <= 0 || sourceNumber == destinationNumber) {
            return false;
        }

        Account sourceAccount = searchAccount(sourceNumber);
        Account destinationAccount = searchAccount(destinationNumber);

        if (sourceAccount == null || destinationAccount == null) {
            return false;
        }

        boolean withdrew = sourceAccount.withdraw(value);

        if (!withdrew) {
            return false;
        }

        destinationAccount.receiveTransfer(value);

        if (destinationAccount.isBonusAccount()) {
            destinationAccount.addBonusPoints(calculateTransferBonusPoints(value));
        }

        return true;
    }

    public int applyInterestToSavingsAccounts(double interestRate) {
        if (interestRate <= 0) {
            return 0;
        }

        int updatedAccounts = 0;

        for (Account account : accounts) {
            if (account.isSavingsAccount()) {
                account.applyInterest(interestRate);
                updatedAccounts++;
            }
        }

        return updatedAccounts;
    }

    private int calculateDepositBonusPoints(double value) {
        return (int) (value / 100);
    }

    private int calculateTransferBonusPoints(double value) {
        return (int) (value / 150);
    }
}