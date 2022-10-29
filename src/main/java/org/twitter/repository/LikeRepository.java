package org.twitter.repository;

import org.twitter.base.repository.BaseRepository;
import org.twitter.entity.Like;

public interface LikeRepository extends BaseRepository<Like,Long> {
    void save(Long tweetId, String accountUsername, String accountPassword);
}
