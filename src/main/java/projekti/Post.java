package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post extends AbstractPersistable<Long> implements Comparable<Post> {

    @ManyToOne
    private Account author;

    @ManyToOne
    private Account recipient;

    private LocalDateTime time;

    @Column
    @NotEmpty
    @Size(min = 1, max = 1000)
    private String content;

    @OneToMany
    @JoinTable(name = "post_comments",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    @OrderBy(value = "time DESC")
    private List<Post> comments = new ArrayList<>();

    private boolean commentStatus = false;

    @ManyToMany
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Account> likes = new ArrayList<>();
    
    @Override
    public int compareTo(Post postToCompare) {
        if (this.getTime().isAfter(postToCompare.getTime())) {
            return -1;
        } else if (this.getTime().isBefore(postToCompare.getTime())) {
            return 1;
        } else {
            return 0;
        }
    }

}
