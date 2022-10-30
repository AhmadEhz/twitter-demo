package org.twitter.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UniqueElements;
import org.twitter.base.entity.BaseEntity;
import org.twitter.entity.dto.AccountDto;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Account extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    @Pattern(regexp = "^[A-Za-z0-9_.]{5,255}$", message = "Username pattern incorrect!")
    private String username;
    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z0-9._$%^&*#!@\\-/\\\\]{8,}$", message = "Password must contains at least 8 character")
    private String password;
    @Column(nullable = false)
    @NotEmpty
    private String name;
    @Email
    private String email;
    @OneToMany(mappedBy = "account")
    private Set<Tweet> tweets;
    @Transient
    private Long followers;
    @Transient
    private Long followings;


    public Account() {
    }

    public Account(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Account(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Account(Long id, String username, String name) {
        setId(id);
        this.username = username;
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof AccountDto)
        {
            return o.equals(this);
        }
        else if(o instanceof Account account)
            return username.equals(account.username) && name.equals(account.name);

        else return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFollowers() {
        return followers;
    }

    public void setFollowers(Long followers) {
        this.followers = followers;
    }

    public Long getFollowings() {
        return followings;
    }

    public void setFollowings(Long followings) {
        this.followings = followings;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name);
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", followers=" + followers +
                ", followings=" + followings +
                "} " + super.toString();
    }
}
