package ai_board.domain.post;

import ai_board.domain.comment.CommentEntity;
import ai_board.domain.emotion.EmotionEntity;
import ai_board.domain.like.LikeEntity;
import ai_board.domain.user.UserEntity;
import ai_board.domain.global.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "posts")
public class PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column @Setter
    private String title;

    @Column @Setter
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private UserEntity user;

    @OneToMany(mappedBy = "post")
    private List<CommentEntity> comments;

    @OneToOne(mappedBy = "post")
    private EmotionEntity emotion;

    @OneToMany(mappedBy = "post")
    private List<LikeEntity> likes = new ArrayList<>();

    @Column @Setter
    private ZonedDateTime createdAt;

    @Column @Setter
    private ZonedDateTime updatedAt;

    @Setter
    @Enumerated(EnumType.STRING) // 한정된 상수값 집합을 정의
    private Status deletedYn = Status.N;

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
