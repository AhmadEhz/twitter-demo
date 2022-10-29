package org.twitter.service;

import org.twitter.base.service.BaseService;
import org.twitter.entity.Like;
import org.twitter.repository.LikeRepository;

public interface LikeService extends BaseService<Like,Long, LikeRepository> {
    void save(Long tweetId, String accountUsername, String accountPassword);
}
