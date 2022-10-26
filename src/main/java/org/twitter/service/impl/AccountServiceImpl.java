package org.twitter.service.impl;

import org.twitter.base.service.BaseServiceImpl;
import org.twitter.entity.Account;
import org.twitter.repository.AccountRepository;
import org.twitter.service.AccountService;

public class AccountServiceImpl extends BaseServiceImpl<Account,Long, AccountRepository> implements AccountService {
    public AccountServiceImpl(AccountRepository repository) {
        super(repository);
    }
}
