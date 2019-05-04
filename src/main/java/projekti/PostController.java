package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    @Autowired
    private AccountService userService;

    @Autowired
    private PostService postService;

    @PostMapping("/{profilePath}/posts")
    public String addPost(@PathVariable String profilePath, @RequestParam String content) {

        /* Katkaistaan pyynnön käsittely, jos postaaja ei ole viestin kohteen ystävä. 
        Kommentointilomake on tässä tapauksessa piilotettu, joten tällaisen pyynnön olisi tultava
        käyttöliittymän ulkopuolelta. */
        if (!userService.checkIfFriendsOrPostingToOwnWall(profilePath)) {
            return "redirect:/" + profilePath;
        }

        postService.addPost(profilePath, content);

        return "redirect:/" + profilePath;
    }

    @PostMapping("/{profilePath}/posts/{id}")
    public String addComment(@PathVariable String profilePath, @PathVariable Long id, @RequestParam String commentContent) {

        Account author = userService.getCurrentUser();
        Account recipient = userService.getByProfilePath(profilePath);

        /* Katkaistaan pyynnön käsittely, jos postaaja ei ole viestin kohteen ystävä. 
        Kommentointilomake on tässä tapauksessa piilotettu, joten tällaisen pyynnön olisi tultava
        käyttöliittymän ulkopuolelta. */
        if (!userService.checkIfFriends(author, recipient) && author != recipient) {
            return "redirect:/" + recipient.getProfilePath();
        }

        postService.addComment(author, recipient, commentContent, id);

        return "redirect:/" + recipient.getProfilePath();
    }

    @PostMapping("/{profilePath}/posts/{id}/like")
    public String handleLike(@PathVariable String profilePath, @PathVariable Long id) {

        Account author = userService.getCurrentUser();
        Account recipient = userService.getByProfilePath(profilePath);

        /* Katkaistaan pyynnön käsittely, jos postaaja ei ole viestin kohteen ystävä. 
        Kommentointilomake on tässä tapauksessa piilotettu, joten tällaisen pyynnön olisi tultava
        käyttöliittymän ulkopuolelta. */
        if (!userService.checkIfFriendsOrPostingToOwnWall(profilePath)) {
            return "redirect:/" + recipient.getProfilePath();
        }

        postService.handleLike(id);
        return "redirect:/" + profilePath;
    }

}
