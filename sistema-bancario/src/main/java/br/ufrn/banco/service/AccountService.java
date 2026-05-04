package br.ufrn.banco.service;

import br.ufrn.banco.model.Account;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final List<Account> accounts = new ArrayList<>();

    public Account registerAccount(int number) {
        Account existentAccount = searchAccount(number);

        if (existentAccount != null) {
            return null;
        }

        Account newAccount = new Account(number);
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

        destinationAccount.deposit(value);
        return true;
    }
}