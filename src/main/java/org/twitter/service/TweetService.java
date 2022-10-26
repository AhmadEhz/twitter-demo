package org.twitter.service;

import org.twitter.base.service.BaseService;
import org.twitter.entity.Tweet;
import org.twitter.repository.TweetRepository;

public interface TweetService extends BaseService<Tweet,Long, TweetRepository> {
}
