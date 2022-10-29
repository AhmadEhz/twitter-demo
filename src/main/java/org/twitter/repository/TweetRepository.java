package org.twitter.repository;

import org.twitter.base.repository.BaseRepository;
import org.twitter.entity.Tweet;
import org.twitter.entity.dto.TweetDto;

import java.util.List;

public interface TweetRepository extends BaseRepository<Tweet,Long> {
    Tweet save(String tweet, String accountUsername, String accountPassword);

    Tweet saveComment(String tweet, Long tweetId, String accountUsername, String accountPassword);

    List<TweetDto> loadAll();

    List<TweetDto> loadComments(long tweetId);
}
