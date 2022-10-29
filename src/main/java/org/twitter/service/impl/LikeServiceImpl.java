package org.twitter.service.impl;

import org.twitter.base.service.BaseServiceImpl;
import org.twitter.entity.Like;
import org.twitter.repository.LikeRepository;
import org.twitter.service.LikeService;

public class LikeServiceImpl extends BaseServiceImpl<Like, Long, LikeRepository> implements LikeService {
    public LikeServiceImpl(LikeRepository repository) {
        super(repository);
    }

    @Override
    public void save(Long tweetId, String accountUsername, String accountPassword) {
        try {
            entityManager.getTransaction().begin();
            repository.save(tweetId, accountUsername, accountPassword);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
