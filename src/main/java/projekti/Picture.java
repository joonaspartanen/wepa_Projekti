package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture extends AbstractPersistable<Long> {

    /*    @Lob // Tämä pitää poistaa, jotta toimii Herokussa.
    @Basic(fetch = FetchType.LAZY)*/
    private byte[] content;

    @NotEmpty
    @Size(min = 1, max = 200)
    private String caption;

    @OneToMany
    @JoinTable(name = "picture_comments",
            joinColumns = @JoinColumn(name = "picture_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    @OrderBy(value = "time DESC")
    private List<Post> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "picture_likes",
            joinColumns = @JoinColumn(name = "picture_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Account> likes = new ArrayList<>();
}
