package org.twitter.repository;

import org.twitter.base.repository.BaseRepository;
import org.twitter.entity.Account;

import java.util.Optional;

public interface AccountRepository extends BaseRepository<Account,Long> {

    Optional<Account> readByUsername(String username);

    Optional<Account> readByPassword(String username, String password);
}
