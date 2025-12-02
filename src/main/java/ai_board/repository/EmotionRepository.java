package ai_board.repository;

import ai_board.domain.emotion.EmotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmotionRepository extends JpaRepository<EmotionEntity, Long> {
    Optional<EmotionEntity> findByPost_PostId(Long postId);
}
