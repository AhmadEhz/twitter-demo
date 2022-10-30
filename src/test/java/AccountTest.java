import org.junit.jupiter.api.*;
import org.twitter.entity.Account;
import org.twitter.entity.Tweet;
import org.twitter.entity.dto.AccountDto;
import org.twitter.util.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountTest {
    private static Account account1 = new Account("first_acc", "12345678", "name");
    private static Account account2 = null;
    private static Account account3 = null;
    private static Tweet tweet1 = null;
    private static Tweet tweet2 = null;

    @BeforeAll
    static void beforeAll() {
        ApplicationContext.getAccountService().save(account1);
        account2 = ApplicationContext.getAccountService().save(
                "second_acc", "12344321", "name2", "abcde@yahoo.com");
        account3 = ApplicationContext.getAccountService().save
                ("third_acc", "123123123", "name3");
        ApplicationContext.getAccountService().detach(account1);
        ApplicationContext.getAccountService().detach(account2);
    }

    @Test
    @Order(8)
    void loadTest() {
        assertEquals(account1, ApplicationContext.getAccountService().loadById(account1.getId()).get());
    }

    @Test
    @Order(2)
    void checkUsername() {
        assertFalse(ApplicationContext.getAccountService().checkUsername("asdfs"));
        assertTrue(ApplicationContext.getAccountService().checkUsername(account1.getUsername()));
    }

    @Test
    @Order(2)
    void checkPassword() {
        assertFalse(ApplicationContext.getAccountService().checkPassword(account1.getUsername(), "123456788"));
        assertTrue(ApplicationContext.getAccountService().checkPassword(account1.getUsername(), "12345678"));
    }

    @Test
    @Order(4)
    void tweetSave() {
        String tweetText = "first tweet";
        tweet1 = ApplicationContext.getTweetService().save(tweetText, account2.getUsername(), account2.getPassword());
        ApplicationContext.getTweetService().detach(tweet1);
    }

    @Test
    @Order(5)
    void commentSave() {
        String commentText = "first comment";
        tweet2 = ApplicationContext.getTweetService().saveComment
                (commentText, tweet1.getId(), account1.getUsername(), account1.getPassword());
        ApplicationContext.getTweetService().detach(tweet2);
    }

    @Test
    @Order(6)
    void secondCommentSave() {
        String commentText = "second comment";
        ApplicationContext.getTweetService().saveComment
                (commentText, tweet2.getId(), account2.getUsername(), account2.getPassword());
    }

    @Test
    @Order(6)
    void addLike() {
        ApplicationContext.getLikeService().save(tweet1.getId(), account2.getUsername(), account2.getPassword());
    }

    @Test
    @Order(8)
    void loadAll() {
        System.out.println(ApplicationContext.getTweetService().loadAll());
    }

    @Test
    @Order(3)
    void follow() {
        ApplicationContext.getAccountService().follow(
                account1.getUsername(), account1.getPassword(), account2.getUsername());
        ApplicationContext.getAccountService().follow(
                account2.getUsername(), account2.getPassword(), account1.getUsername());
        ApplicationContext.getAccountService().follow(
                account3.getUsername(), account3.getPassword(), account2.getUsername());
        ApplicationContext.getAccountService().follow(
                account3.getUsername(), account3.getPassword(), account1.getUsername());
    }

    @Test
    @Order(4)
    void unfollow() {
        ApplicationContext.getAccountService().unfollow(
                account1.getUsername(), account1.getPassword(), account2.getUsername());
    }

    @Test
    @Order(8)
    void loadFollowers() {
        ApplicationContext.getAccountService().loadById(1L);
        List<AccountDto> list = ApplicationContext.getAccountService().loadFollowers(account1.getUsername());
        List<Account> savedAccounts = new ArrayList<>();
        savedAccounts.add(account2);
        savedAccounts.add(account3);
        assertEquals(2, list.size());
        assertEquals(savedAccounts, list);
    }

    @Test
    @Order(8)
    void loadFollowings() {
        List<AccountDto> list = ApplicationContext.getAccountService().loadFollowings(account2.getUsername());
        assertEquals(1, list.size());
        assertEquals(account1, list.get(0));
    }

    @Test
    @Order(9)
    void updateTweet() throws InterruptedException {
        ApplicationContext.getTweetService().update("updated tweet", tweet1.getId()
                , tweet1.getAccount().getUsername(), tweet1.getAccount().getPassword());
        Thread.sleep(3000);
        assertNotEquals(tweet1, ApplicationContext.getTweetService().loadById(tweet1.getId()).get());
    }
}
