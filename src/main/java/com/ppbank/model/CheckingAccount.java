package com.ppbank.model;

/**
 * Checking account: charges a fixed fee on every withdrawal.
 */
public class CheckingAccount implements AccountType {

    private static final double WITHDRAW_FEE = 0.50;

    @Override
    public double calculateWithdrawFee(double amount) {
        return WITHDRAW_FEE;
    }

    @Override
    public String getDescription() {
        return "Checking Account";
    }
}
