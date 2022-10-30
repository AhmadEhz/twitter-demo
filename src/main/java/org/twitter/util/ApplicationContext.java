package org.twitter.util;

import org.twitter.repository.AccountRepository;
import org.twitter.repository.TweetRepository;
import org.twitter.repository.impl.AccountRepositoryImpl;
import org.twitter.repository.impl.TweetRepositoryImpl;
import org.twitter.service.AccountService;
import org.twitter.service.TweetService;
import org.twitter.service.impl.AccountServiceImpl;
import org.twitter.service.impl.TweetServiceImpl;

public class ApplicationContext {
    private static final AccountRepository accountRepository
            = new AccountRepositoryImpl(HibernateUtil.createEntityManager());
    private static final TweetRepository tweetRepository
            = new TweetRepositoryImpl(HibernateUtil.createEntityManager());

    private static final AccountService accountService
            = new AccountServiceImpl(accountRepository);
    private static final TweetService tweetService
            = new TweetServiceImpl(tweetRepository);


    public static AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public static TweetRepository getTweetRepository() {
        return tweetRepository;
    }

    public static AccountService getAccountService() {
        return accountService;
    }

    public static TweetService getTweetService() {
        return tweetService;
    }
}
