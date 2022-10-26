package org.twitter.repository.impl;

import org.twitter.base.repository.BaseRepositoryImpl;
import org.twitter.entity.Account;
import org.twitter.repository.AccountRepository;

import javax.persistence.EntityManager;

public class AccountRepositoryImpl extends BaseRepositoryImpl<Account,Long> implements AccountRepository {
    public AccountRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Account> getEntityClass() {
        return Account.class;
    }
}
