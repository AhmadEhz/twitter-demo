package org.twitter.service.impl;

import org.twitter.base.service.BaseServiceImpl;
import org.twitter.entity.Account;
import org.twitter.entity.dto.AccountDto;
import org.twitter.repository.AccountRepository;
import org.twitter.service.AccountService;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl extends BaseServiceImpl<Account, Long, AccountRepository> implements AccountService {
    public AccountServiceImpl(AccountRepository repository) {
        super(repository);
    }

    @Override
    public Account save(String username, String password, String name) {
        Account account = new Account(username, password, name);
        checkEntity(account);
        save(account);
        return account;
    }

    @Override
    public Account save(String username, String password, String name, String email) {
        Account account = new Account(username, password, name, email);
        checkEntity(account);
        save(account);
        return account;
    }

    @Override
    public Optional<AccountDto> loadAccount(String username) {
        return repository.readByUsername(username);
    }

    @Override
    public Optional<Account> loadAccount(String username, String password) {
        return repository.readByPassword(username, password);
    }

    @Override
    public void changeEmail(String username, String password, String newEmail) {
        repository.updateEmail(username, password, newEmail);
    }

    @Override
    public void changeUsername(String newUsername, String oldUsername, String password) {
        Account account = new Account();
        account.setUsername(newUsername);
        checkEntity(account);
        repository.updateUsername(newUsername, oldUsername, password);
    }

    @Override
    public void changePassword(String newPassword, String username, String oldPassword) {
        Account account = new Account();
        account.setPassword(newPassword);
        checkEntity(account);
        repository.updatePassword(newPassword, username, oldPassword);
    }

    @Override
    public void changeName(String newName, String username, String password) {
        Account account = new Account();
        account.setName(newName);
        checkEntity(account);
        repository.updateName(newName,username,password);
    }

    @Override
    public boolean checkUsername(String username) {
        return repository.readByUsername(username).isPresent();
    }

    @Override
    public boolean checkPassword(String username, String password) {
        return repository.readByPassword(username, password).isPresent();
    }

    @Override
    public void follow(String followingUsername, String followingPassword, String followedUsername) {
        execute(() -> repository.follow(
                followingUsername, followingPassword, followedUsername));
    }

    @Override
    public void unfollow(String followingUsername, String followingPassword, String followedUsername) {
        execute(() -> repository.unfollow(followingUsername, followingPassword, followedUsername));
    }

    @Override
    public List<AccountDto> loadFollowers(String username) {
        return repository.loadFollowers(username);
    }

    @Override
    public List<AccountDto> loadFollowings(String username) {
        return repository.loadFollowings(username);
    }
}
