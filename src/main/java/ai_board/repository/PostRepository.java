package ai_board.repository;

import ai_board.domain.global.Status;
import ai_board.domain.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    List<PostEntity> findAllByDeletedYn(Status deletedYn);

}
