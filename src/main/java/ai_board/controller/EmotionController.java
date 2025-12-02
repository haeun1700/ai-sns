package ai_board.controller;

import ai_board.domain.emotion.EmotionEntity;
import ai_board.domain.post.PostEntity;
import ai_board.repository.EmotionRepository;
import ai_board.service.EmotionService;
import ai_board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;
    private final PostService postService;
    private final EmotionRepository emotionRepository;

    @PostMapping("/analyze/{postId}")
    public ResponseEntity<Map<String, String>> analyzeEmotion(@PathVariable Long postId) {
        // 1. 게시글 가져오기
        PostEntity post = postService.getPostEntity(postId);

        // 2. 감정 분석
        String emotion = emotionService.analyzeEmotion(post.getContent());

        // 3. 추천 노래
        String song = emotionService.recommendSong(emotion);

        // 4. EmotionEntity 생성 및 저장
        EmotionEntity entity = new EmotionEntity();
        entity.setPost(post);
        entity.setUser(post.getUser()); // 게시글 작성자 기준
        entity.setRecommendedSong(song);
        emotionRepository.save(entity);

        // 5. 응답 반환
        return ResponseEntity.ok(Map.of(
                "emotion", emotion,
                "recommendedSong", song
        ));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Map<String, String>> getEmotionResult(@PathVariable Long postId) {
        EmotionEntity emotion = emotionRepository.findByPost_PostId(postId)
                .orElseThrow(() -> new RuntimeException("감정 분석 결과 없음"));

        return ResponseEntity.ok(Map.of(
                "emotion", emotion.getRecommendedSong(),  // 실제 감정 분석 결과 저장하면 여기 넣기
                "recommendedSong", emotion.getRecommendedSong()
        ));
    }
}
