package org.twitter.service.impl;

import org.twitter.base.service.BaseServiceImpl;
import org.twitter.entity.Tweet;
import org.twitter.entity.dto.TweetDto;
import org.twitter.repository.TweetRepository;
import org.twitter.service.TweetService;
import org.twitter.util.HibernateUtil;

import jakarta.validation.ConstraintViolation;
import org.twitter.util.exception.CustomizedIllegalArgumentException;

import java.util.List;
import java.util.Set;

public class TweetServiceImpl extends BaseServiceImpl<Tweet, Long, TweetRepository> implements TweetService {
    public TweetServiceImpl(TweetRepository repository) {
        super(repository);
    }

    @Override
    public Tweet save(String tweet, String accountUsername, String accountPassword) {
        checkTweet(new Tweet(tweet));
        Tweet tweet1 = null;
        try {
            entityManager.getTransaction().begin();
            tweet1 = repository.save(tweet, accountUsername, accountPassword);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return tweet1;
    }

    @Override
    public Tweet saveComment(String tweet, Long tweetId, String accountUsername, String accountPassword) {
       return repository.saveComment(tweet, tweetId, accountUsername, accountPassword);
    }

    @Override
    public List<TweetDto> loadAll() {
        return repository.loadAll();
    }

    private void checkTweet(Tweet tweet) {
        Set<ConstraintViolation<Tweet>> constraintViolations = HibernateUtil.getValidator().validate(tweet);
        if (!constraintViolations.isEmpty())
            throw new CustomizedIllegalArgumentException(constraintViolations.toString());
    }
}
