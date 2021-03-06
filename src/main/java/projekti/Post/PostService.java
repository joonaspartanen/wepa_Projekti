package projekti.Post;

import projekti.Account.AccountService;
import projekti.Account.AccountRepository;
import projekti.Account.Account;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private AccountService userService;

    public Post addPost(String profilePath, String content) {

        Account author = userService.getCurrentUser();
        Account recipient = userService.getByProfilePath(profilePath);

        Post post = new Post();
        post.setAuthor(author);
        post.setRecipient(recipient);
        post.setTime(LocalDateTime.now());
        post.setContent(content);

        postRepository.save(post);
        recipient.getPosts().add(post);
        userRepository.save(recipient);

        return post;
    }

    public Post addComment(Account author, Account recipient, String content, Long id) {

        Post comment = new Post();
        comment.setAuthor(author);
        comment.setRecipient(recipient);
        comment.setTime(LocalDateTime.now());
        comment.setCommentStatus(true);
        comment.setContent(content.trim());
        postRepository.save(comment);

        Post post = postRepository.getOne(id);
        post.getComments().add(comment);
        postRepository.save(post);

        return post;
    }

    public void handleLike(Long id) {

        Account user = userService.getCurrentUser();
        Post post = postRepository.getOne(id);

        if (post.getLikes().contains(user)) {
            post.getLikes().remove(user);
            postRepository.save(post);
            return;
        }

        post.getLikes().add(user);
        postRepository.save(post);
        return;
    }

}
