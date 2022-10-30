package org.twitter.entity.dto;

import org.twitter.entity.Tweet;

import java.time.LocalDateTime;

public class TweetDto {

    private AccountDto account;

    private String text;

    private long likeCount;

    private LocalDateTime time;

    private LocalDateTime updateAt;

    public TweetDto() {
    }

    public TweetDto(Tweet tweet) {
        account = new AccountDto(tweet.getAccount());
        setTweet(tweet);
    }

    public TweetDto(Tweet tweet, AccountDto account) {
        this.account = account;
        setTweet(tweet);
    }
    private void setTweet(Tweet tweet) {
        text = tweet.getText();
        likeCount = tweet.getLikeCount();
        time = tweet.getTime();
        updateAt = tweet.getUpdatedAt();
    }


    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "account=" + account.toString(false) +
                ", text='" + text + '\'' +
                ", likes=" + likeCount +
                ", time=" + time +
                ", updateAt=" + updateAt;
    }
}
