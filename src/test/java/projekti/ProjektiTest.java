package projekti;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    public void testUserCreation() {

        Account testAccount = userService.createNewUser("Test user", "testpassword", "A really short presentation.", "testpath");

        assertEquals("A really short presentation.", userRepository.findByUsername("Test user").getPresentation());
        assertEquals("testpath", userRepository.findByUsername("Test user").getProfilePath());
    }
}
