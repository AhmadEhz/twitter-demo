package org.twitter.entity;

import org.twitter.base.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Account extends BaseEntity<Long> {
    private String username;
    private String password;
    private String name;
    @OneToMany
    private Set<Tweet> tweets;
    @OneToMany
    private Set<Account> followers;
    @OneToMany
    private Set<Account> followings;
}
