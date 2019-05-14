package projekti.Account;

import projekti.Account.Account;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = {"profilepic", "friends"})
    Account findByUsername(String username);

    @EntityGraph(attributePaths = {"profilepic", "friends"})
    Account findByProfilePath(String profilePath);

    @Override
    @EntityGraph(attributePaths = {"profilepic", "friends"})
    List<Account> findAll();

    @EntityGraph(attributePaths = {"profilepic", "friends"})
    List<Account> findAllByOrderByUsernameAsc();
}
