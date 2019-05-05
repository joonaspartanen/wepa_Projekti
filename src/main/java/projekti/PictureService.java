package projekti;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PictureService {

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private AccountService userService;

    @Autowired
    private PostRepository postRepository;

    public Account addProfilePicture(MultipartFile file) throws IOException {

        Picture picture = new Picture();
        picture.setContent(file.getBytes());
        pictureRepository.save(picture);

        Account user = userService.getCurrentUser();
        user.setProfilepic(picture);
        userRepository.save(user);

        return user;
    }

    public void addPicture(MultipartFile file, String caption) throws IOException {

        Account user = userService.getCurrentUser();
        if (!file.getContentType().equals("image/jpeg")) {
            return;
        }

        if (user.getPictures().size() < 10) {
            Picture picture = new Picture();
            picture.setContent(file.getBytes());
            picture.setCaption(caption);
            pictureRepository.save(picture);

            user.getPictures().add(picture);
            userRepository.save(user);
        }
    }

    public List<Picture> getAllPictures(Account user) {
        return user.getPictures();
    }

    public void commentPicture(Account author, Account recipient, Long id, String commentContent) {

        LocalDateTime time = LocalDateTime.now();

        Picture pictureToComment = pictureRepository.getOne(id);

        if (!userService.checkIfFriendsOrPostingToOwnWall(recipient.getProfilePath())) {
            return;
        }

        Post comment = new Post();
        comment.setAuthor(author);
        comment.setRecipient(recipient);
        comment.setTime(time);
        comment.setContent(commentContent);
        comment.setCommentStatus(true);

        postRepository.save(comment);

        pictureToComment.getComments().add(comment);
        pictureRepository.save(pictureToComment);
    }

    public void handlePictureLike(String profilePath, Long id) {

        Account currentUser = userService.getCurrentUser();
        Picture picture = pictureRepository.getOne(id);

        if (!userService.checkIfFriendsOrPostingToOwnWall(profilePath)) {
            return;
        }

        System.out.println(picture.getLikes().contains(currentUser));

        if (picture.getLikes().contains(currentUser)) {
            picture.getLikes().remove(currentUser);
            pictureRepository.save(picture);
            return;
        }

        picture.getLikes().add(currentUser);
        pictureRepository.save(picture);
    }

    public void deletePicture(String profilePath, Long id) {
        Picture picture = pictureRepository.getOne(id);
        Account user = userService.getByProfilePath(profilePath);

        // Estetään toisten käyttäjien kuvien poistaminen käyttöliittymän ulkopuolelta.
        if (user != userService.getCurrentUser()) {
            return;
        }

        if (user.getProfilepic() == picture) {
            user.setProfilepic(null);
        }

        user.getPictures().remove(picture);
        userRepository.save(user);
        pictureRepository.delete(picture);
    }

    public void setAsProfilePicture(String profilePath, Long id) {
        Account user = userService.getByProfilePath(profilePath);

        // Estetään toisten käyttäjien kuvien manipulointi käyttöliittymän ulkopuolelta.
        if (user != userService.getCurrentUser()) {
            return;
        }

        user.setProfilepic(pictureRepository.getOne(id));
        userRepository.save(user);
    }
}
