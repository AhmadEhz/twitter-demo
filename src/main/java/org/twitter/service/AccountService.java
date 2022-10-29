package org.twitter.service;

import org.twitter.base.service.BaseService;
import org.twitter.entity.Account;
import org.twitter.repository.AccountRepository;

import java.util.Optional;

public interface AccountService extends BaseService<Account,Long, AccountRepository> {
    void createAccount(String name, String username, String password);

    Optional<Account> loadAccount(String username);

    Optional<Account> loadAccount(String username, String password);

    boolean checkUsername(String username);

    boolean checkPassword(String username, String password);
}
