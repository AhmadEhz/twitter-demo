package org.twitter.entity;

import org.twitter.base.entity.BaseEntity;
import org.twitter.entity.id.FollowId;

import javax.persistence.*;

@Entity
@IdClass(FollowId.class)
public class Follow extends BaseEntity<Long> {
    @Id
    @ManyToOne
    private Account following;
    @ManyToOne
    @Id
    private Account follower;
}
