package org.twitter.repository.impl;

import org.twitter.base.repository.BaseRepositoryImpl;
import org.twitter.entity.Account;
import org.twitter.repository.AccountRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.function.Supplier;

public class AccountRepositoryImpl extends BaseRepositoryImpl<Account, Long> implements AccountRepository {
    public AccountRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public Optional<Account> readByUsername(String username) {
        String query = "select id, username, name from Account where username=:uname";
        Optional<Object[]> optional = getSingleResult(() -> entityManager.createQuery(query, Object[].class)
                .setParameter("uname", username));
        if (optional.isEmpty())
            return Optional.empty();
        return Optional.of(new Account(
                (Long) optional.get()[0]
                , (String) optional.get()[1]
                , (String) optional.get()[2]));
    }

    @Override
    public Optional<Account> readById(Long id) {
        String query = "select id, username, name from Account where id=:id";
        Optional<Object[]> optional = getSingleResult(() -> entityManager.createQuery(query, Object[].class)
                .setParameter("id", id));
        if (optional.isEmpty())
            return Optional.empty();
        return Optional.of(new Account(
                (Long) optional.get()[0]
                , (String) optional.get()[1]
                , (String) optional.get()[2]));
    }

    @Override
    public Optional<Account> readByPassword(String username, String password) {
        String query = "select Account from Account where username = :uname and password = :pass";
        return getSingleResult(() -> entityManager.createQuery(query, Account.class)
                .setParameter("uname", username).setParameter("pass", password));
    }

    private <T> Optional<T> getSingleResult(Supplier<TypedQuery<T>> supplier) {
        try {
            return Optional.ofNullable(supplier.get().getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
