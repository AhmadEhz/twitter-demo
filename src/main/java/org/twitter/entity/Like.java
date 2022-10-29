package org.twitter.entity;

import org.twitter.base.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
public class Like extends BaseEntity<Long> {
    @ManyToOne
    private Account account;
    @ManyToOne
    private Tweet tweet;
}
