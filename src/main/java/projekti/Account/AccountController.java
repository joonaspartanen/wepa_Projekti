package projekti.Account;

import projekti.Account.Account;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Picture.PictureService;
import projekti.Post.PostRepository;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private AccountService userService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("errorMsg", "Your username and password are invalid.");
        }

        if (logout != null) {
            model.addAttribute("msg", "You have been logged out successfully.");
        }

        return "login";
    }

    @GetMapping("/{profilePath}")
    private String getUserPage(Model model, @PathVariable String profilePath) {

        Account user = userRepository.findByProfilePath(profilePath);
        model.addAttribute("user", user);

        model.addAttribute("friendRequests", user.getReceivedFriendRequests());
        model.addAttribute("friends", user.getFriends());
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("pictures", pictureService.getAllPictures(user));

        Pageable pageable = PageRequest.of(0, 25, Sort.by("time").descending());
        model.addAttribute("posts", postRepository.findAllByRecipientAndCommentStatus(user, false, pageable));

        return "user";
    }

    @GetMapping("/users")
    private String getAllUsers(Model model) {

        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("users", userService.getAllOtherUsers());

        return "users";
    }

    @GetMapping("/{profilePath}/friends")
    private String getAllFriends(@PathVariable String profilePath, Model model) {

        Account user = userService.getByProfilePath(profilePath);

        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("user", user);
        model.addAttribute("friends", user.getFriends());

        return "friends";
    }

    @PostMapping("/users")
    private String searchUsers(Model model, @RequestParam String search) {

        model.addAttribute("users", userService.getAllOtherUsers());
        model.addAttribute("currentUser", userService.getCurrentUser());

        return "users";
    }

    @PostMapping("/users/friendRequest")
    private String sendFriendRequest(Model model, @RequestParam Long friendId) {

        userService.addFriendRequest(friendId);
        return "redirect:/users";
    }

    @PostMapping("/{profilePath}/acceptFriendRequest")
    private String acceptFriendRequest(@RequestParam Long friendId, @PathVariable String profilePath) {

        userService.acceptFriendRequest(friendId);
        return "redirect:/" + profilePath;
    }

    @PostMapping("/{profilePath}/declineFriendRequest")
    private String declineFriendRequest(@RequestParam Long friendId, @PathVariable String profilePath) {

        userService.declineFriendRequest(friendId);
        return "redirect:/" + profilePath;
    }

    @GetMapping("/signup")
    public String getSignupForm(@ModelAttribute Account user) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute Account user, BindingResult bindingResult, @RequestParam String username,
            @RequestParam String password, @RequestParam String presentation,
            @RequestParam String profilePath) throws IOException {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.createNewUser(username, password, presentation, profilePath);

        return "redirect:/login";
    }

}
