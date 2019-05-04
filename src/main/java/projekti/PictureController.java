package projekti;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

@Controller
public class PictureController {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private AccountService userService;

    @GetMapping("/{profilePath}/pics")
    public String getPics(Model model, @PathVariable String profilePath) {

        Account user = userService.getByProfilePath(profilePath);

        model.addAttribute("pictures", pictureService.getAllPictures(user));
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("user", user);

        return "pics";
    }

    @PostMapping("/pics")
    public String addPicture(@RequestParam("file") MultipartFile file, @RequestParam("caption") String caption) throws IOException {

        Account user = userService.getCurrentUser();
        
        pictureService.addPicture(file, caption);

        return "redirect:/" + user.getProfilePath() + "/pics";
    }

    @GetMapping(path = "/pics/{id}/content", produces = "image/jpg")
    @ResponseBody
    public byte[] getContent(@PathVariable Long id) {
        return pictureRepository.getOne(id).getContent();
    }

    @PostMapping("/{profilePath}/pics/{id}")
    public String commentPicture(@PathVariable String profilePath, @PathVariable Long id, @RequestParam String commentContent) {

        Account author = userService.getCurrentUser();
        Account recipient = userService.getByProfilePath(profilePath);

        pictureService.commentPicture(author, recipient, id, commentContent);
        return "redirect:/" + recipient.getProfilePath() + "/pics";
    }

    @PostMapping("/{profilePath}/pics/{id}/like")
    public String handlePictureLike(@PathVariable String profilePath, @PathVariable Long id) {

        pictureService.handlePictureLike(profilePath, id);

        return "redirect:/" + profilePath + "/pics";
    }

    @PostMapping("/{profilePath}/pics/{id}/delete")
    public String deletePicture(@PathVariable String profilePath, @PathVariable Long id) {

        pictureService.deletePicture(profilePath, id);

        return "redirect:/" + profilePath + "/pics";
    }

    @PostMapping("/{profilePath}/pics/{id}/profilePic")
    public String setAsProfilePicture(@PathVariable String profilePath, @PathVariable Long id) {

        pictureService.setAsProfilePicture(profilePath, id);

        return "redirect:/" + profilePath + "/pics";
    }
}
