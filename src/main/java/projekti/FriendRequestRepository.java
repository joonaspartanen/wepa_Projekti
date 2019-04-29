package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    FriendRequest findByRequesterAndFriend(Account requester, Account friend);

}
