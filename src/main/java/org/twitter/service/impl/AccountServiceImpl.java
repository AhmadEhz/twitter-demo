package org.twitter.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.twitter.base.service.BaseServiceImpl;
import org.twitter.entity.Account;
import org.twitter.repository.AccountRepository;
import org.twitter.service.AccountService;
import org.twitter.util.HibernateUtil;
import org.twitter.util.exception.CustomizedIllegalArgumentException;

import java.util.Optional;
import java.util.Set;

public class AccountServiceImpl extends BaseServiceImpl<Account, Long, AccountRepository> implements AccountService {
    public AccountServiceImpl(AccountRepository repository) {
        super(repository);
    }

    @Override
    public void createAccount(String username, String password, String name) {
        Account account = new Account(username,password,name);
        checkAccount(account);
        save(account);
    }
    @Override
    public Optional<Account> loadAccount(String username) {
        return repository.readByUsername(username);
    }
    @Override
    public Optional<Account> loadAccount(String username, String password) {
        return repository.readByPassword(username,password);
    }
    @Override
    public boolean checkUsername(String username) {
        return repository.readByUsername(username).isPresent();
    }
    @Override
    public boolean checkPassword(String username, String password) {
        return repository.readByPassword(username,password).isPresent();
    }

    private void checkAccount(Account account) {
        Validator validator = HibernateUtil.getValidator();
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        if(!constraintViolations.isEmpty()) {
            throw new CustomizedIllegalArgumentException(constraintViolations.toString());
        }
    }
}
