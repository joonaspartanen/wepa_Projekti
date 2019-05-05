package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = {"profilePath", "username", "profilepic", "friends"})
    Account findByUsername(String username);

    @EntityGraph(attributePaths = {"profilePath", "username", "profilepic", "friends"})
    Account findByProfilePath(String profilePath);

    @Override
    @EntityGraph(attributePaths = {"profilePath", "username", "profilepic", "friends"})
    List<Account> findAll();

    @EntityGraph(attributePaths = {"profilePath", "username", "profilepic", "friends"})
    List<Account> findAllByOrderByUsernameAsc();
}
