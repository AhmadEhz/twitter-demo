package org.twitter.entity.dto;

import org.twitter.entity.Account;
import org.twitter.entity.Like;
import org.twitter.entity.Tweet;

import java.util.HashSet;
import java.util.Set;

public class AccountDto {
    private String username;
    private String name;
    private Set<TweetDto> tweets;

    public AccountDto(Account account) {
        this(account, true);
    }

    public AccountDto(Account account, boolean setTweets) {
        username = account.getUsername();
        name = account.getName();
        if(setTweets) {//To avoid StackOverFlow exception.
            tweets = new HashSet<>();
            for (Tweet t : account.getTweets()) {
                tweets.add(new TweetDto(t, this));
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TweetDto> getTweets() {
        return tweets;
    }

    public void setTweets(Set<TweetDto> tweets) {
        this.tweets = tweets;
    }

    @Override
    public String toString() {
        return toString(true);
    }
    public String toString(boolean returnTweets) {
        return "username= " + username +
                ", name= " + name +
                (returnTweets ? ", tweets= " + tweets : "");
    }
}
