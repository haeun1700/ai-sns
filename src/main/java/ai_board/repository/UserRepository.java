package ai_board.repository;

import ai_board.domain.global.Status;
import ai_board.domain.user.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findAllByDeletedYn(Status status);
}
