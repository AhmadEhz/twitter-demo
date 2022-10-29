import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.twitter.entity.Account;
import org.twitter.util.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private static Account account = new Account("assss", "12345678", "name");

    @BeforeAll
    static void beforeAll() {
        ApplicationContext.getAccountService().save(account);

    }

    @Test
    void testn() {
        assertFalse(ApplicationContext.getAccountService().checkUsername("asdfs"));
        assertEquals(account, ApplicationContext.getAccountService().loadById(account.getId()).get());
    }

    @Test
    void tweetSave() {
        String tweetText = "first tweet";
        ApplicationContext.getTweetService().save(tweetText, account.getUsername(), account.getPassword());
    }

    @Test
    void commentSave() {
        String commentText = "first comment";
        ApplicationContext.getTweetService().saveComment(commentText,1L,"assss","12345678");
    }

    @Test
    void checkTweet() {
        ApplicationContext.getTweetService().save("first tweet", account.getUsername(), account.getPassword());
    }

    @Test
    void addLike() {
        ApplicationContext.getLikeService().save(1L, "assss", "12345678");
    }

    @Test
    void loadAll() {
        System.out.println(ApplicationContext.getTweetService().loadAll());
    }
}
