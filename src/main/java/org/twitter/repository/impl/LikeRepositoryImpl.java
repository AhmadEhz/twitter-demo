package org.twitter.repository.impl;

import org.twitter.base.repository.BaseRepositoryImpl;
import org.twitter.entity.Like;
import org.twitter.repository.LikeRepository;
import org.twitter.util.exception.CustomizedIllegalArgumentException;

import javax.persistence.EntityManager;

public class LikeRepositoryImpl extends BaseRepositoryImpl<Like, Long> implements LikeRepository {
    public LikeRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Like> getEntityClass() {
        return Like.class;
    }

    @Override
    public void save(Long tweetId, String accountUsername, String accountPassword) {
        String query = """
                insert into likes(tweet_id, account_id)
                values (:tweetId
                , (select id from account where username = :uname and password = :pass))""";
        int update = entityManager.createNativeQuery(query)
                .setParameter("tweetId", tweetId)
                .setParameter("uname", accountUsername)
                .setParameter("pass", accountPassword)
                .executeUpdate();
        if (update < 1)
            throw new CustomizedIllegalArgumentException("Username or password is incorrect!");
    }
}
