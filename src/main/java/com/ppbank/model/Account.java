package com.ppbank.model;

import com.ppbank.exception.InsufficientBalanceException;

/**
 * Account entity: responsible only for keeping and protecting its own balance.
 * The fee rule varies per account type (AccountType), injected at creation
 * time and never checked here with "if/instanceof" (Liskov: any AccountType
 * can be used without breaking the account's behavior).
 */
public class Account {

    private final String number;
    private final String owner;
    private final AccountType type;
    private double balance;

    public Account(String number, String owner, AccountType type) {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Account number is required");
        }
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner is required");
        }
        this.number = number;
        this.owner = owner;
        this.type = type;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than zero");
        }
        double total = amount + type.calculateWithdrawFee(amount);
        if (total > balance) {
            throw new InsufficientBalanceException(number);
        }
        this.balance -= total;
    }

    public String getNumber() {
        return number;
    }

    public String getOwner() {
        return owner;
    }

    public AccountType getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }
}
