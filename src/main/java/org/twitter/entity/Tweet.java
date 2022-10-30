package org.twitter.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.twitter.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tweet extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;
    @Size(max = 280, message = "Characters of tweets is shouldn't ")
    @Column(nullable = false, columnDefinition = "text")
    private String text;
    @OneToMany(mappedBy = "tweet")
    private Set<Like> likes;
    @Transient
    private long likeCount;
    @OneToOne
    @JoinColumn(name = "comment_for", nullable = true)
    private Tweet commentFor;
    @CreationTimestamp
    private LocalDateTime time;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Tweet() {
    }

    public Tweet(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updateAt) {
        this.updatedAt = updateAt;
    }

    public Tweet getComment() {
        return commentFor;
    }

    public void setComment(Tweet commentFor) {
        this.commentFor = commentFor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equals(account, tweet.account)
                && Objects.equals(text, tweet.text) && Objects.equals(commentFor, tweet.commentFor)
                && Objects.equals(time, tweet.time) && Objects.equals(updatedAt, tweet.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, text, commentFor, time, updatedAt);
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "account=" + account +
                ", text='" + text + '\'' +
                ", likeCount=" + likeCount +
                ", time=" + time +
                ", updateAt=" + updatedAt +
                "} " + super.toString();
    }

}
