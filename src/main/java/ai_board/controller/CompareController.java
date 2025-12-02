package ai_board.controller;

import ai_board.domain.user.UserEntity;
import ai_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class CompareController {

    private final UserRepository userRepository;

    @GetMapping("/compare-lazy-eager")
    public ResponseEntity<String> compareFetchType() {
        long start = System.currentTimeMillis();

        List<UserEntity> users = userRepository.findAll(); // 1번 쿼리
        for (UserEntity user : users) {
            System.out.println("유저"+ user.getUsername());
            System.out.println("게시글 수"+ user.getPost().size());
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        return ResponseEntity.ok("총 걸린 시간: " + duration + "ms");
    }
    @GetMapping("/users-with-posts")
    public String testNPlusOne() {
        List<UserEntity> users = userRepository.findAll();  // 1번 쿼리

        for (UserEntity user : users) {
            System.out.println(user.getPost().size());  // 유저마다 게시글 쿼리 (N번)
        }

        return "n+1쿼리 발생 완료";
    }

}
