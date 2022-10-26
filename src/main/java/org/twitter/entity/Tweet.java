package org.twitter.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.twitter.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Tweet extends BaseEntity<Long> {
    @ManyToOne
    private Account account;
    private String text;
    @CreationTimestamp
    private LocalDateTime time;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Account> likedAccount;
    @Transient
    private long likes;
}
