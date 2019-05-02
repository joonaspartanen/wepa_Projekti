package projekti;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjektiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService userService;

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private PostService postService;

    @Test
    public void userCreationTest() {

        Account testAccount = userService.createNewUser("Test user", "testpassword", "A really short presentation.", "testpath");

        assertEquals("A really short presentation.", userRepository.findByUsername("Test user").getPresentation());
        assertEquals("testpath", userRepository.findByUsername("Test user").getProfilePath());
    }

    @Test
    public void checkIfFriendsTest() {
        Account testAccount1 = userService.createNewUser("Test user 1", "testpassword", "A really short presentation.", "testpath1");
        Account testAccount2 = userService.createNewUser("Test user 2", "testpassword", "A really short presentation.", "testpath2");

        testAccount1.getFriends().add(testAccount2);
        testAccount2.getFriends().add(testAccount1);

        assertTrue(userService.checkIfFriends(testAccount1, testAccount2));
    }

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk());
    }

}
