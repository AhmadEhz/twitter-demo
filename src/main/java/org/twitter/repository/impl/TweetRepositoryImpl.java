package org.twitter.repository.impl;

import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.twitter.base.repository.BaseRepositoryImpl;
import org.twitter.entity.Tweet;
import org.twitter.entity.dto.TweetDto;
import org.twitter.repository.TweetRepository;
import org.twitter.util.exception.CustomizedIllegalArgumentException;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TweetRepositoryImpl extends BaseRepositoryImpl<Tweet, Long> implements TweetRepository {
    public TweetRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Tweet> getEntityClass() {
        return Tweet.class;
    }

    @Override
    public Tweet save(String tweet, String accountUsername, String accountPassword) {
        return save(tweet, null, accountUsername, accountPassword);
    }

    @Override
    public Tweet saveComment(String tweet, Long tweetId, String accountUsername, String accountPassword) {
        return save(tweet, tweetId, accountUsername, accountPassword);
    }

    private Tweet save(String tweet, Long tweetId, String accountUsername, String accountPassword) {
        String sql = """
                insert into tweet(text, time, updateat, comment_for, account_id)
                values (:tweet, :time, :update, :comment
                ,(select id from account where username = :uname and password = :pass))
                returning id""";
        LocalDateTime time = LocalDateTime.now();
        Long id = (Long) entityManager.createNativeQuery(sql)
                .setParameter("tweet", tweet)
                .setParameter("time", time)
                .setParameter("update", time)
                .setParameter("comment", tweetId)
                .setParameter("uname", accountUsername)
                .setParameter("pass", accountPassword)
                .setParameter("comment", new TypedParameterValue(StandardBasicTypes.LONG, tweetId))
                //Insert nullable value.
                ////If "comment_for" is null, it means this tweet is not a comment.
                .getSingleResult();
        if(id == null || id ==0)
            throw new CustomizedIllegalArgumentException("username or password is incorrect!");
        return readById(id).orElseThrow(() ->
                new CustomizedIllegalArgumentException("Your tweet is not saved!"));
    }

    @Override
    public List<TweetDto> loadAll() {
        String query = """
                select t ,count(l) from Tweet as t
                left join Like as l on t.id = l.tweet.id
                where t.commentFor is null
                group by t
                order by t.time desc
                """;
        //If "commentFor" is null, it means this tweet is not a comment.
        //Left join to show tweets that not have like.
        List<Object[]> result = entityManager.createQuery(query, Object[].class).getResultList();
        List<TweetDto> tweetDto = new ArrayList<>();
        for (Object[] o : result) {
            Tweet t = (Tweet) o[0];
            t.setLikeCount((long) o[1]);
            tweetDto.add(new TweetDto(t));
        }
        return tweetDto;
    }

    @Override
    public List<TweetDto> loadComments(long tweetId) {
        String query = "select t from Tweet as t where t.commentFor.id = :tweetId";
        List<Tweet> result = entityManager.createQuery(query, Tweet.class)
                .setParameter("tweetId", tweetId).getResultList();
        List<TweetDto> tweets = new ArrayList<>();
        for (Tweet t : result) {
            tweets.add(new TweetDto(t));
        }
        return tweets;
    }
}
