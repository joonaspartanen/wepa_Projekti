package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 4, max = 20)
    private String username;

    @NotEmpty
    @Size(min = 8, max = 60)
    private String password;

    @NotEmpty
    @Size(min = 4, max = 30)
    private String profilePath;

    @NotEmpty
    @Size(min = 10, max = 300)
    private String presentation;

    @ManyToMany
    @JoinTable(name = "account_friends",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Account> friends = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    private List<FriendRequest> receivedFriendRequests = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    private List<FriendRequest> sentFriendRequests = new ArrayList<>();

    @OneToOne
    private Picture profilepic;

    @OneToMany
    @OrderBy(value = "id DESC")
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany
    private List<Post> posts = new ArrayList<>();
}
