package ai_board.service;

import ai_board.domain.like.LikeEntity;
import ai_board.domain.post.PostEntity;
import ai_board.domain.user.UserEntity;
import ai_board.repository.LikeRepository;
import ai_board.repository.PostRepository;
import ai_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    // like 추가
    public boolean toggleLike(Long postId, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다,"));

        Optional<LikeEntity> existing = likeRepository.findByUserAndPost(user, post);

        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            return false; // 좋아요 취소
        } else {
            LikeEntity likeEntity = new LikeEntity();
            likeEntity.setUser(user);
            likeEntity.setPost(post);
            likeRepository.save(likeEntity);
            return true;
        }
    }
    public long countlike(Long postId){
        return likeRepository.countByPost_PostId(postId);
    }
}
