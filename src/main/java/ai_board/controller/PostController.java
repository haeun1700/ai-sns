package ai_board.controller;

import ai_board.domain.like.LikeRequest;
import ai_board.domain.post.PostDto;
import ai_board.domain.post.PostRequest;
import ai_board.domain.user.UserEntity;
import ai_board.service.LikeService;
import ai_board.service.PostService;
import ai_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostRequest postRequest) {
        UserEntity user = userService.findById(postRequest.userId());
        PostDto post = postService.create(postRequest, user);
        return ResponseEntity.ok(post);
        //ResponseEntity: HTTP 응답으로 PostDto를 바디에 담아서 보내줄게1
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostsByUser(@RequestParam Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequest postRequest
    ) {
        try {
            PostDto updatedPost = postService.update(id, postRequest);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // "해당 게시글을 찾을 수 없습니다."
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletedPost(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // "게시글을 찾을 수 없습니다." or "이미 삭제됨"
        }
    }
    @PostMapping("/{id}/like")
    public ResponseEntity<String> toggelLike(@PathVariable Long id, @RequestBody LikeRequest likeRequest) {
        boolean liked = likeService.toggleLike(id, likeRequest.userId());
        return ResponseEntity.ok(liked ? "좋아요" : "좋아요 취소");
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<Long> getLikes(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.countlike(postId));
    }
}
