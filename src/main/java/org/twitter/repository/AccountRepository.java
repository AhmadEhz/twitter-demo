package org.twitter.repository;

import org.twitter.base.repository.BaseRepository;
import org.twitter.entity.Account;
import org.twitter.entity.dto.AccountDto;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends BaseRepository<Account,Long> {
    Optional<Account> readById(Long id);

    Optional<AccountDto> readByUsername(String username);

    Optional<Account> readByPassword(String username, String password);

    void updateEmail(String newEmail, String username, String password);

    void updateUsername(String newUsername, String oldUsername, String password);

    void updatePassword(String newPassword, String username, String oldPassword);

    void updateName(String newName, String username, String password);

    void follow(String followingUsername, String followingPassword, String followedUsername);

    void unfollow(String followingUsername, String followingPassword, String followedUsername);

    List<AccountDto> loadFollowers(String username);

    List<AccountDto> loadFollowings(String username);
}
