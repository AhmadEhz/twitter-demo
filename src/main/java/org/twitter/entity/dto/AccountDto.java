package org.twitter.entity.dto;

import org.twitter.entity.Account;
import org.twitter.entity.Like;
import org.twitter.entity.Tweet;

import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AccountDto {
    private String username;
    private String name;
    private Set<TweetDto> tweets;

    private Long followers;

    private Long followings;

    public AccountDto(Account account) {
        this(account, true);
    }

    public AccountDto(Account account, boolean setTweets) {
        username = account.getUsername();
        name = account.getName();
        followers = account.getFollowers();
        followings = account.getFollowings();
        if (setTweets && account.getTweets() != null) {//To avoid StackOverFlow exception.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        AccountDto account = null;
        if (o instanceof Account) {
            account = new AccountDto((Account) o);
        } else if (o instanceof AccountDto) {
            account = (AccountDto) o;
        } else return false;
        return Objects.equals(username, account.username) && Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name);
    }
}
