package com.ppbank.model;

/**
 * Savings account: no withdrawal fee.
 */
public class SavingsAccount implements AccountType {

    @Override
    public double calculateWithdrawFee(double amount) {
        return 0.0;
    }

    @Override
    public String getDescription() {
        return "Savings Account";
    }
}
