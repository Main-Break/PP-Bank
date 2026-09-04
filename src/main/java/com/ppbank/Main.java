package com.ppbank;

import com.ppbank.model.CheckingAccount;
import com.ppbank.model.SavingsAccount;
import com.ppbank.repository.AccountRepository;
import com.ppbank.repository.InMemoryAccountRepository;
import com.ppbank.service.AccountService;

public class Main {

    public static void main(String[] args) {
        AccountRepository repository = new InMemoryAccountRepository();
        AccountService accountService = new AccountService(repository);

        accountService.openAccount("001", "Kainan H.", new CheckingAccount());
        accountService.openAccount("002", "Maria Silva", new SavingsAccount());

        accountService.deposit("001", 1000.0);
        accountService.deposit("002", 500.0);

        accountService.transfer("001", "002", 200.0);

        accountService.withdraw("002", 100.0);

        System.out.printf("Account 001 - balance: %.2f%n", accountService.getBalance("001"));
        System.out.printf("Account 002 - balance: %.2f%n", accountService.getBalance("002"));
    }
}
