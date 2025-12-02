package ai_board;

import ai_board.domain.post.PostEntity;
import ai_board.domain.user.UserEntity;
import ai_board.repository.UserRepository;
import ai_board.domain.global.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 1000; i++) {
            UserEntity user = new UserEntity();
            user.setUsername("user" + i);
            user.setPassword("pass" + i);
            user.setDeletedYn(Status.N);

            // 각 유저에게 게시글 3개씩 생성
            for (int j = 1; j <= 3; j++) {
                PostEntity post = new PostEntity();
                post.setTitle("user" + i + "의 게시글 " + j);
                post.setContent("이건 테스트용 내용입니다.");
                post.setUser(user); // 단방향 연관
                user.getPost().add(post); // 양방향 연관
            }

            userRepository.save(user); // Cascade.ALL이면 Post도 자동 저장됨
        }
    }
}
