package com.ppbank.repository;

import com.ppbank.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * Persistence abstraction for accounts. AccountService depends only on this
 * interface, never on a concrete implementation (Dependency Inversion) -
 * swapping in-memory storage for a real database doesn't require changing
 * any business rule.
 */
public interface AccountRepository {

    void save(Account account);

    Optional<Account> findByNumber(String number);

    List<Account> findAll();
}
