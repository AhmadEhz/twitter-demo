package org.twitter.service;

import org.twitter.base.service.BaseService;
import org.twitter.entity.Account;
import org.twitter.entity.dto.AccountDto;
import org.twitter.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

public interface AccountService extends BaseService<Account,Long, AccountRepository> {
    Account save(String name, String username, String password);

    Account save(String username, String password, String name, String email);

    Optional<AccountDto> loadAccount(String username);

    Optional<Account> loadAccount(String username, String password);

    void changeEmail(String username, String password, String newEmail);

    void changeUsername(String newUsername, String oldUsername, String password);

    void changePassword(String newPassword, String username, String oldPassword);

    void changeName(String newName, String username, String password);

    boolean checkUsername(String username);

    boolean checkPassword(String username, String password);

    void follow(String followingUsername, String followingPassword, String followedPassword);

    void unfollow(String followingUsername, String followingPassword, String followedPassword);

    List<AccountDto> loadFollowers(String username);

    List<AccountDto> loadFollowings(String username);
}
