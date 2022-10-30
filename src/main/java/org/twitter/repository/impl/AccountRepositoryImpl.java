package org.twitter.repository.impl;

import org.twitter.base.repository.BaseRepositoryImpl;
import org.twitter.entity.Account;
import org.twitter.entity.dto.AccountDto;
import org.twitter.repository.AccountRepository;
import org.twitter.util.QueryUtil;
import org.twitter.util.exception.CustomizedIllegalArgumentException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl extends BaseRepositoryImpl<Account, Long> implements AccountRepository {
    public AccountRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public Optional<AccountDto> readByUsername(String username) {
        String query = """
                select acc.id, acc.username, acc.email, acc.name, count(fo.following.id) as follower_count, count(f.follower.id) as following_count from Account as acc
                left join Follow as fo on acc.id = fo.follower.id
                left join Follow f on acc.id = f.following.id
                where acc.username = :uname
                group by acc.id""";
        Object[] objects = QueryUtil.getQueryResult(() -> entityManager.createQuery(query, Object[].class)
                .setParameter("uname", username)).orElse(null);
        if (objects == null)
            return Optional.empty();
        Account account = setResult(objects);
        return Optional.of(new AccountDto(account));
    }

    @Override
    //Can't return AccountDto.
    public Optional<Account> readById(Long id) {
        String query = """
                select acc.id, acc.username, acc.email, acc.name, count(fo.following.id) as follower_count, count(f.follower.id) as following_count from Account as acc
                left join Follow as fo on acc.id = fo.follower.id
                left join Follow f on acc.id = f.following.id
                where acc.id = :id
                group by acc.id""";
        Object[] objects = QueryUtil.getQueryResult(() -> entityManager.createQuery(query, Object[].class)
                .setParameter("id", id)).orElse(null);
        if (objects == null)
            return Optional.empty();
        Account account = setResult(objects);
        return Optional.of(account);
    }

    @Override
    public Optional<Account> readByPassword(String username, String password) {
        String query = """
                select acc.id, acc.username, acc.email, acc.name, count(fo.following.id) as follower_count
                , count(f.follower.id) as following_count , acc.password from Account as acc
                left join Follow as fo on acc.id = fo.follower.id
                left join Follow f on acc.id = f.following.id
                where acc.username = :uname and acc.password = :pass
                group by acc.id""";
        Object[] objects = QueryUtil.getQueryResult(() -> entityManager.createQuery(query, Object[].class)
                .setParameter("uname", username)
                .setParameter("pass",password)).orElse(null);
        if (objects == null)
            return Optional.empty();
        Account account = setResult(objects);
        account.setPassword((String) objects[6]);
        return Optional.of(account);
    }

    @Override
    public void updateEmail(String newEmail, String username, String password) {
        String query = """
                update Account
                set email = :email
                where username = :uname
                and password = :pass""";
        QueryUtil.executeQuery(() -> entityManager.createQuery(query)
                        .setParameter("email", newEmail)
                        .setParameter("uname", username)
                        .setParameter("pass", password)
                , () -> new CustomizedIllegalArgumentException("Your username or password is incorrect!"));
    }

    @Override
    public void updateUsername(String newUsername, String oldUsername, String password) {
        String query = """
                update Account
                set username = :newUname
                where username = :oldUname
                and password = :pass""";
        QueryUtil.executeQuery(() -> entityManager.createQuery(query)
                        .setParameter("newUname", newUsername)
                        .setParameter("oldUname", oldUsername)
                        .setParameter("pass", password)
                , () -> new CustomizedIllegalArgumentException("Your username or password is incorrect!"));
    }

    @Override
    public void updatePassword(String newPassword, String username, String oldPassword) {
        String query = """
                update Account
                set password = :newPass
                where username = :uname
                and password = :oldPass""";
        QueryUtil.executeQuery(() -> entityManager.createQuery(query)
                        .setParameter("newPass", newPassword)
                        .setParameter("uname", username)
                        .setParameter("oldPass", oldPassword)
                , () -> new CustomizedIllegalArgumentException("Your username or password is incorrect!"));
    }

    @Override
    public void updateName(String newName, String username, String password) {
        String query = """
                update Account
                set name = :name
                where username = :uname
                and password = :pass""";
        QueryUtil.executeQuery(() -> entityManager.createQuery(query)
                        .setParameter("name", newName)
                        .setParameter("uname", username)
                        .setParameter("pass", password)
                , () -> new CustomizedIllegalArgumentException("Your username or password is incorrect!"));
    }

    @Override
    public void follow(String followingUsername, String followingPassword, String followedUsername) {
        String query = """
                insert into follow (follower_id, following_id)
                values ((select id from account where username = ?)
                , (select id from account where username = ? and password = ?))""";
        QueryUtil.executeQuery(() -> entityManager.createNativeQuery(query)
                        .setParameter(1, followedUsername)
                        .setParameter(2, followingUsername)
                        .setParameter(3, followingPassword)
                , () -> new CustomizedIllegalArgumentException
                        ("Your username or password or followed username is incorrect!"));
    }

    @Override
    public void unfollow(String followingUsername, String followingPassword, String followedUsername) {
        String sql = """
                delete from follow
                where follower_id = (select id from account where username = :uname1)
                and following_id = (select id from account where username = :uname2 and password = :pass)
                """;
        QueryUtil.executeQuery(() -> entityManager.createNativeQuery(sql)
                        .setParameter("uname1", followedUsername)
                        .setParameter("uname2", followingUsername)
                        .setParameter("pass", followingPassword)
                , () -> new CustomizedIllegalArgumentException
                        ("Your Username or password or followed username is incorrect!"));
    }

    @Override
    public List<AccountDto> loadFollowers(String username) {
        String query = """
                select acc from Account as acc
                join Follow as fo on acc.id = fo.following.id
                where fo.follower.username = :uname""";
        return getFollows(query, username);
    }

    @Override
    public List<AccountDto> loadFollowings(String username) {
        String query = """
                select acc from Account as acc
                join Follow as fo on acc.id = fo.follower.id
                where fo.following.username = :uname""";
        return getFollows(query, username);
    }


    private List<AccountDto> getFollows(String query, String username) {
        List<Account> resultList = entityManager.createQuery(query, Account.class)
                .setParameter("uname", username)
                .getResultList();
        List<AccountDto> accounts = new ArrayList<>();
        for (Account account : resultList) {
            accounts.add(new AccountDto(account));
        }
        return accounts;
    }

    private Account setResult(Object[] objects) {
        Account account = new Account();
        account.setId((Long) objects[0]);
        account.setUsername((String) objects[1]);
        account.setEmail((String) objects[2]);
        account.setName((String) objects[3]);
        account.setFollowers((Long) objects[4]);
        account.setFollowings((Long) objects[5]);
        return account;
    }
}
