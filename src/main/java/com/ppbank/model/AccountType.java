package com.ppbank.model;

/**
 * Abstraction for each account type's fee rule.
 * New account types are added by creating a new implementation,
 * with no change to Account or the existing types (Open/Closed).
 */
public interface AccountType {

    double calculateWithdrawFee(double amount);

    String getDescription();
}
