package br.ufrn.banco.ui;

import br.ufrn.banco.model.Account;
import br.ufrn.banco.service.AccountService;

import java.util.Scanner;

public class ConsoleMenu {

    private final AccountService accountService = new AccountService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        int option;

        do {
            showHeader();
            showMenu();

            option = readInt("Choose an option: ");

            switch (option) {
                case 1 -> createAccount();
                case 2 -> consultAccount();
                case 3 -> checkBalance();
                case 4 -> deposit();
                case 5 -> withdraw();
                case 6 -> transfer();
                case 7 -> applyInterest();
                case 0 -> showMessage("Shutting down system. Goodbye!");
                default -> showMessage("Invalid option. Please try again.");
            }

            if (option != 0) {
                waitForEnter();
            }

        } while (option != 0);
    }

    private void showHeader() {
        System.out.println();
        System.out.println("====================================");
        System.out.println("         BANKING SYSTEM");
        System.out.println("====================================");
    }

    private void showMenu() {
        System.out.println("1. Create Account");
        System.out.println("2. Consultar Conta");
        System.out.println("3. Consultar Saldo");
        System.out.println("4. Deposit");
        System.out.println("5. Withdraw");
        System.out.println("6. Transfer");
        System.out.println("7. Apply Interest");
        System.out.println("0. Exit");
        System.out.println("------------------------------------");
    }

    private void createAccount() {
        showSection("Create Account");

        int accountNumber = readInt("Enter account number: ");
        int accountType = readInt("Account type (1 - Common, 2 - Bonus, 3 - Savings): ");
        double initialBalance = readDouble("Enter initial balance: ");

        if (initialBalance < 0) {
            showMessage("Initial balance must be non-negative.");
            return;
        }

        Account account;

        if (accountType == 1) {
            account = accountService.registerAccount(accountNumber, initialBalance);
        } else if (accountType == 2) {
            account = accountService.registerBonusAccount(accountNumber, initialBalance);
        } else if (accountType == 3) {
            account = accountService.registerSavingsAccount(accountNumber, initialBalance);
        } else {
            showMessage("Invalid account type.");
            return;
        }

        if (account == null) {
            showMessage("An account with this number already exists.");
        } else {
            showMessage("Account created successfully!");
        }
    }

    private void consultAccount() {
        showSection("Consultar Conta");

        int accountNumber = readInt("Enter account number: ");
        Account account = accountService.searchAccount(accountNumber);

        if (account == null) {
            showMessage("Account not found.");
        } else {
            System.out.println("------------------------------------");
            System.out.printf("Account Number: %d%n", account.getNumber());
            System.out.printf("Account Type: %s%n", getAccountType(account));
            System.out.printf("Balance: $%.2f%n", account.getBalance());

            if (account.isBonusAccount()) {
                System.out.printf("Bonus Points: %d%n", account.getBonusPoints());
            }

            System.out.println("------------------------------------");
        }
    }

    private void checkBalance() {
        showSection("Consultar Saldo");

        int accountNumber = readInt("Enter account number: ");
        Account account = accountService.searchAccount(accountNumber);

        if (account == null) {
            showMessage("Account not found.");
        } else {
            System.out.printf("Balance: $%.2f%n", account.getBalance());
        }
    }

    private void deposit() {
        showSection("Deposit");

        int accountNumber = readInt("Enter account number: ");
        double amount = readDouble("Enter deposit amount: ");

        boolean success = accountService.deposit(accountNumber, amount);

        if (success) {
            showMessage("Deposit completed successfully!");
        } else {
            showMessage("Unable to complete deposit.");
        }
    }

    private void withdraw() {
        showSection("Withdraw");

        int accountNumber = readInt("Enter account number: ");
        double amount = readDouble("Enter withdrawal amount: ");

        boolean success = accountService.withdraw(accountNumber, amount);

        if (success) {
            showMessage("Withdrawal completed successfully!");
        } else {
            showMessage("Unable to complete withdrawal.");
        }
    }

    private void transfer() {
        showSection("Transfer");

        int sourceAccount = readInt("Enter source account number: ");
        int destinationAccount = readInt("Enter destination account number: ");
        double amount = readDouble("Enter transfer amount: ");

        boolean success = accountService.transfer(sourceAccount, destinationAccount, amount);

        if (success) {
            showMessage("Transfer completed successfully!");
        } else {
            showMessage("Unable to complete transfer.");
        }
    }

    private void showSection(String title) {
        System.out.println();
        System.out.println("------------ " + title + " ------------");
    }

    private void showMessage(String message) {
        System.out.println();
        System.out.println(">> " + message);
    }

    private int readInt(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    private double readDouble(String message) {
        System.out.print(message);
        return scanner.nextDouble();
    }

    private void waitForEnter() {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }

    private String getAccountType(Account account) {
        if (account.isBonusAccount()) {
            return "Bonus";
        }

        if (account.isSavingsAccount()) {
            return "Savings";
        }

        return "Common";
    }

    private void applyInterest() {
        showSection("Apply Interest");

        double interestRate = readDouble("Enter interest rate: ");

        int updatedAccounts = accountService.applyInterestToSavingsAccounts(interestRate);

        if (updatedAccounts == 0) {
            showMessage("No savings accounts were updated.");
        } else {
            showMessage("Interest applied to " + updatedAccounts + " savings account(s).");
        }
    }
}
