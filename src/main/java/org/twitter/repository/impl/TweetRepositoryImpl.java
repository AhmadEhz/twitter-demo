package org.twitter.repository.impl;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.twitter.base.repository.BaseRepositoryImpl;
import org.twitter.entity.Tweet;
import org.twitter.entity.dto.TweetDto;
import org.twitter.repository.TweetRepository;
import org.twitter.util.QueryUtil;
import org.twitter.util.exception.CustomizedIllegalArgumentException;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TweetRepositoryImpl extends BaseRepositoryImpl<Tweet, Long> implements TweetRepository {
    public TweetRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Tweet> getEntityClass() {
        return Tweet.class;
    }

    @Override
    public Long save(String tweet, String accountUsername, String accountPassword) {
        return save(tweet, null, accountUsername, accountPassword);
    }

    @Override
    public Long saveComment(String tweet, Long tweetId, String accountUsername, String accountPassword) {
        return save(tweet, tweetId, accountUsername, accountPassword);
    }

    private Long save(String tweet, Long tweetId, String accountUsername, String accountPassword) {
        Session session = entityManager.unwrap(Session.class);
        AtomicReference<Long> id = new AtomicReference<>(null);
        try {
            session.doWork((connection) -> {
                String sql = """
                        insert into tweet(text, time, updated_at, comment_for, account_id)
                        values (?, ?, ?, ?
                        ,(select id from account where username = ? and password = ?))
                        returning id""";
                Timestamp time = new Timestamp(new java.util.Date().getTime());
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, tweet);
                ps.setTimestamp(2, time);
                ps.setTimestamp(3, time);
                if (tweetId == null)
                    ps.setNull(4, Types.BIGINT);
                else
                    ps.setLong(4, tweetId);
                ps.setString(5, accountUsername);
                ps.setString(6, accountPassword);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    id.set(rs.getLong(1));
            });
        } catch (ConstraintViolationException e) {
            //If account_id is null (because of incorrect username or password), this exception is thrown.
            throw new CustomizedIllegalArgumentException("Your username or password is incorrect!", e);
        }
        if (id.get() == null)
            throw new CustomizedIllegalArgumentException("Your username or password is incorrect!");
        return id.get();
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

    @Override
    public void like(Long tweetId, String accountUsername, String accountPassword) {
        String query = """
                insert into likes(tweet_id, account_id)
                values (:tweetId
                , (select id from account where username = :uname and password = :pass))""";
        QueryUtil.executeQuery(() -> entityManager.createNativeQuery(query)
                        .setParameter("tweetId", tweetId)
                        .setParameter("uname", accountUsername)
                        .setParameter("pass", accountPassword),
                () -> new CustomizedIllegalArgumentException("Your username or password is incorrect!"));
    }

    @Override
    public void unlike(Long tweetId, String accountUsername, String accountPassword) {
        String query = """
                delete from likes
                where tweet_id = :tweetId
                and account_id = (select id from account where username = :uname and password = :pass)""";

        QueryUtil.executeQuery(() -> entityManager.createNativeQuery(query)
                        .setParameter("tweetId", tweetId)
                        .setParameter("uname", accountUsername)
                        .setParameter("pass", accountPassword)
                , () -> new CustomizedIllegalArgumentException("Your username or password is incorrect!"));
    }

    @Override
    public void update(String text, Long tweetId, String accountUsername, String accountPassword) {
        String query = """
                update Tweet set text = :text , updatedAt = :upAt
                where id = :id
                and account.id = (select id from Account where username = :uname and password = :pass)""";
        QueryUtil.executeQuery(() -> entityManager.createQuery(query)
                        .setParameter("text", text)
                        .setParameter("id", tweetId)
                        .setParameter("uname", accountUsername)
                        .setParameter("pass", accountPassword)
                        .setParameter("upAt", LocalDateTime.now())
                , () -> new CustomizedIllegalArgumentException("Your username or password is incorrect!"));

    }
}
