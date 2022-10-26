package org.twitter.service;

import org.twitter.base.service.BaseService;
import org.twitter.entity.Account;
import org.twitter.repository.AccountRepository;

public interface AccountService extends BaseService<Account,Long, AccountRepository> {
}
