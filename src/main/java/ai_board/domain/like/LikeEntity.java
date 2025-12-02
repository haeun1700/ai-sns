package ai_board.domain.like;

import ai_board.domain.post.PostEntity;
import ai_board.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "likes")
public class LikeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    private PostEntity post;
}
