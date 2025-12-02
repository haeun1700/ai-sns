package ai_board.repository;

import ai_board.domain.like.LikeEntity;
import ai_board.domain.post.PostEntity;
import ai_board.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);
    long countByPost_PostId(long postId);
}
