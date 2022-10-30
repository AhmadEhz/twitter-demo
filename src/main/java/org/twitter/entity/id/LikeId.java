package org.twitter.entity.id;

import org.twitter.entity.Account;
import org.twitter.entity.Tweet;

import javax.persistence.IdClass;
import java.io.Serializable;

public class LikeId implements Serializable {
    private Account account;
    private Tweet tweet;
}
