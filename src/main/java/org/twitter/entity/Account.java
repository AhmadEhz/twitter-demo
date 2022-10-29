package org.twitter.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.twitter.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class Account extends BaseEntity<Long> {
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9_.]{5,255}$", message = "Username pattern incorrect!")
    private String username;
    @Pattern(regexp = "^[A-Za-z0-9._$%^&*#!@\\-/\\\\]{8,}$",message = "Password must contains at least 8 character")
    private String password;
    private String name;
    @Email
    private String email;
    @OneToMany(mappedBy = "account")
    private Set<Tweet> tweets;
    @OneToMany(mappedBy = "account")
    private Set<Like> likedTweets;
    @OneToMany
    private Set<Account> followers;
    @OneToMany
    private Set<Account> followings;


    public Account() {
    }

    public Account(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
    public Account(Long id, String username, String name) {
        setId(id);
        this.username = username;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return username.equals(account.username)&& name.equals(account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(Set<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Set<Like> getLikedTweets() {
        return likedTweets;
    }

    public void setLikedTweets(Set<Like> likedTweets) {
        this.likedTweets = likedTweets;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", likedTweets=" + likedTweets +
                ", followers=" + followers +
                ", followings=" + followings +
                "} " + super.toString();
    }
}
