package ai_board.domain.user;

import ai_board.domain.comment.CommentEntity;
import ai_board.domain.emotion.EmotionEntity;
import ai_board.domain.global.Status;
import ai_board.domain.like.LikeEntity;
import ai_board.domain.post.PostEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true) @Setter
    private String username;

    @Column @Setter
    private String password;

    @Column
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime updatedAt;

    @Setter @Enumerated(EnumType.STRING)
    private Status deletedYn = Status.N;

    @Setter @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // 외래키가 있는 것이 주인.
    private List<PostEntity> post = new ArrayList<>(); // 엔티티의 연관관계니까 당연히 dto x, 엔티티와 매핑

    @Setter @OneToMany(mappedBy = "user")
    private List<CommentEntity> comment = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<EmotionEntity> emotions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikeEntity> likeEntities = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }
}
