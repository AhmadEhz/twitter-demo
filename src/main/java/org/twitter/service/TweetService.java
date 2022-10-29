package org.twitter.service;

import org.twitter.base.service.BaseService;
import org.twitter.entity.Tweet;
import org.twitter.entity.dto.TweetDto;
import org.twitter.repository.TweetRepository;

import java.util.List;

public interface TweetService extends BaseService<Tweet,Long, TweetRepository> {
    Tweet save(String tweet, String accountUsername, String accountPassword);

    Tweet saveComment(String tweet, Long tweetId, String accountUsername, String accountPassword);

    List<TweetDto> loadAll();
}
