package org.twitter.repository.impl;

import org.twitter.base.repository.BaseRepositoryImpl;
import org.twitter.entity.Tweet;
import org.twitter.repository.TweetRepository;

import javax.persistence.EntityManager;

public class TweetRepositoryImpl extends BaseRepositoryImpl<Tweet,Long> implements TweetRepository {
    public TweetRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Tweet> getEntityClass() {
        return Tweet.class;
    }
}
