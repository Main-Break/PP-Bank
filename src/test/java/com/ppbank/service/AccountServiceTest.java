package com.ppbank.service;

import com.ppbank.exception.AccountNotFoundException;
import com.ppbank.exception.InsufficientBalanceException;
import com.ppbank.model.CheckingAccount;
import com.ppbank.model.SavingsAccount;
import com.ppbank.repository.AccountRepository;
import com.ppbank.repository.InMemoryAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        AccountRepository repository = new InMemoryAccountRepository();
        accountService = new AccountService(repository);
    }

    @Test
    void shouldDepositAndUpdateBalance() {
        accountService.openAccount("001", "Kainan H.", new SavingsAccount());
        accountService.deposit("001", 100.0);

        assertEquals(100.0, accountService.getBalance("001"));
    }

    @Test
    void shouldChargeWithdrawFeeOnCheckingAccount() {
        accountService.openAccount("001", "Kainan H.", new CheckingAccount());
        accountService.deposit("001", 100.0);
        accountService.withdraw("001", 50.0);

        assertEquals(49.50, accountService.getBalance("001"));
    }

    @Test
    void shouldNotWithdrawWithoutSufficientBalance() {
        accountService.openAccount("001", "Kainan H.", new SavingsAccount());
        accountService.deposit("001", 10.0);

        assertThrows(InsufficientBalanceException.class, () -> accountService.withdraw("001", 50.0));
    }

    @Test
    void shouldTransferBetweenAccounts() {
        accountService.openAccount("001", "Kainan H.", new CheckingAccount());
        accountService.openAccount("002", "Maria Silva", new SavingsAccount());
        accountService.deposit("001", 100.0);

        accountService.transfer("001", "002", 30.0);

        assertEquals(69.50, accountService.getBalance("001"));
        assertEquals(30.0, accountService.getBalance("002"));
    }

    @Test
    void shouldThrowErrorWhenAccountNotFound() {
        assertThrows(AccountNotFoundException.class, () -> accountService.getBalance("999"));
    }
}
