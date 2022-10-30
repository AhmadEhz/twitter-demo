package org.twitter.entity.id;

import org.twitter.entity.Account;

import java.io.Serializable;

public class FollowId implements Serializable {
    private Account following;
    private Account follower;
}
