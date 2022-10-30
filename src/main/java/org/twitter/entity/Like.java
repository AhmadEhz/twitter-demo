package org.twitter.entity;

import org.twitter.base.entity.BaseEntity;
import org.twitter.entity.id.LikeId;

import javax.persistence.*;

@Entity
@IdClass(LikeId.class)
@Table(name = "likes")
public class Like extends BaseEntity<Long> {
    @Id
    @ManyToOne
    private Account account;
    @Id
    @ManyToOne
    private Tweet tweet;
}
