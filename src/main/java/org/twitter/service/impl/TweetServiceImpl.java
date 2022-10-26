package org.twitter.service.impl;

import org.twitter.base.service.BaseServiceImpl;
import org.twitter.entity.Tweet;
import org.twitter.repository.TweetRepository;
import org.twitter.service.TweetService;

public class TweetServiceImpl extends BaseServiceImpl<Tweet,Long, TweetRepository> implements TweetService {
    public TweetServiceImpl(TweetRepository repository) {
        super(repository);
    }

}
