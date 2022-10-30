package org.twitter.entity.dto;

import org.twitter.entity.Account;
import org.twitter.entity.Tweet;

public class LikeDto {
    private AccountDto account;
    private TweetDto tweet;
    public LikeDto() {}
    public LikeDto(Account account, Tweet tweet) {
        this.account = new AccountDto(account);
        this.tweet = new TweetDto(tweet);
    }
    public LikeDto(AccountDto account, TweetDto tweet) {
        this.account = account;
        this.tweet = tweet;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }
    public void setAccount (Account account) {
        this.account = new AccountDto(account);
    }

    public TweetDto getTweet() {
        return tweet;
    }

    public void setTweet(TweetDto tweet) {
        this.tweet = tweet;
    }
    public void setTweet(Tweet tweet) {
        this.tweet = new TweetDto(tweet);
    }
}
