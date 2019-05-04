package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account createNewUser(String username, String password, String presentation, String profilePath) {

        Account user = new Account();

        // Katkaistaan metodin suoritus, jos käyttäjänimi tai profiilipolku on jo varattu.
        if (userRepository.findByUsername(username) != null || userRepository.findByProfilePath(profilePath) != null) {
            return user;
        }

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setPresentation(presentation);
        user.setProfilePath(profilePath.trim());

        userRepository.save(user);
        return user;
    }

    public Account getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = userRepository.findByUsername(auth.getName());
        return currentUser;
    }

    public boolean checkIfFriends(Account user1, Account user2) {

        return user1.getFriends().contains(user2);
    }

    public boolean checkIfFriendsOrPostingToOwnWall(String profilePath) {

        Account author = getCurrentUser();
        Account recipient = getByProfilePath(profilePath);

        return author.getFriends().contains(recipient) || author == recipient;
    }

    public List<Account> getFriendRequestsAsUserObjects(Account user) {

        List<Account> friends = new ArrayList<>();
        for (FriendRequest friendRequest : user.getReceivedFriendRequests()) {
            friends.add(friendRequest.getRequester());
        }

        return friends;
    }

    public Account getByProfilePath(String profilePath) {
        return userRepository.findByProfilePath(profilePath);
    }

    public List<Account> getAllOtherUsers() {

        Account currentUser = getCurrentUser();
        List<Account> users = userRepository.findAll();
        users.remove(currentUser);

        return users;
    }

    public void addFriend(Long friendId) {

        Account currentUser = getCurrentUser();
        Account friend = userRepository.getOne(friendId);

        if (!currentUser.getFriends().contains(friend)) {
            currentUser.getFriends().add(friend);
            friend.getFriends().add(currentUser);
            userRepository.save(currentUser);
            userRepository.save(friend);
        }
    }

    public void addFriendRequest(Long friendId) {

        Account currentUser = getCurrentUser();
        Account friend = userRepository.getOne(friendId);

        if (currentUser.getFriends().contains(friend) || getFriendRequestsAsUserObjects(friend).contains(currentUser)) {
            return;
        }

        FriendRequest friendRequest = new FriendRequest(currentUser, friend, LocalDateTime.now());
        friend.getReceivedFriendRequests().add(friendRequest);
        userRepository.save(friend);
    }

    public void acceptFriendRequest(Long friendId) {

        Account currentUser = getCurrentUser();
        addFriend(friendId);
        FriendRequest requestToRemove = friendRequestRepository.findByRequesterAndFriend(userRepository.getOne(friendId), currentUser);
        currentUser.getReceivedFriendRequests().remove(requestToRemove);
        userRepository.save(currentUser);
    }

    public void declineFriendRequest(Long friendId) {

        Account currentUser = getCurrentUser();
        FriendRequest requestToRemove = friendRequestRepository.findByRequesterAndFriend(userRepository.getOne(friendId), currentUser);
        currentUser.getReceivedFriendRequests().remove(requestToRemove);
        userRepository.save(currentUser);
    }
}
