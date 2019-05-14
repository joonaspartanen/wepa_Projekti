package projekti.Post;

import projekti.Account.Account;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"comments"})
    List<Post> findAllByRecipientAndCommentStatus(Account user, boolean comment, Pageable pageable);

}
