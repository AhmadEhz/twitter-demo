package org.twitter.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.twitter.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tweet extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    @NotNull
    @Size(min = 1, max = 280)
    @Column(columnDefinition = "text")
    private String text;
    @OneToMany(mappedBy = "tweet")
    private Set<Like> likes;
    @Transient
    private long likeCount;
    @OneToOne
    @JoinColumn(name = "comment_for")
    private Tweet commentFor;
    @CreationTimestamp
    private LocalDateTime time;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    public Tweet() {
    }

    public Tweet(String text) {
        this.text = text;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Tweet getComment() {
        return commentFor;
    }

    public void setComment(Tweet commentFor) {
        this.commentFor = commentFor;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "account=" + account +
                ", text='" + text + '\'' +
                ", likeCount=" + likeCount +
                ", time=" + time +
                ", updateAt=" + updateAt +
                "} " + super.toString();
    }

}
