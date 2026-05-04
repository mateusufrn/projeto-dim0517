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

    public boolean creditar(int number, double value) {
        Account account = searchAccount(number);

        if (account == null || value <= 0) {
            return false;
        }

        account.credit(value);
        return true;
    }
}