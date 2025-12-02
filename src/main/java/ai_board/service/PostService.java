package ai_board.service;

import ai_board.domain.global.Status;
import ai_board.domain.post.PostDto;
import ai_board.domain.post.PostEntity;
import ai_board.domain.post.PostRequest;
import ai_board.domain.user.UserEntity;
import ai_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    // c
    public PostDto create(PostRequest postRequest, UserEntity user) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postRequest.title());
        postEntity.setContent(postRequest.content());
        postEntity.setUser(user);
        postRepository.save(postEntity);

        return PostDto.from(postEntity);
    }
    public PostEntity getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
    //r
    public PostDto getPost(Long id){
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        return PostDto.from(postEntity);
    }
    public List<PostDto> getAllPosts(){
        return postRepository.findAllByDeletedYn(Status.N).stream().map(PostDto::from).toList();
        // stream : 리스트를 하나씩 처리할 수 있도록 스트림으로 변경
        // map : 각 post객체를 dto로 변환
    }
    //u
    public PostDto update(Long id, PostRequest postRequest) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        postEntity.setTitle(postRequest.title());
        postEntity.setContent(postRequest.content());

        return PostDto.from(postEntity);
    }
    //d
    public void deletedPost(Long postId){
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if(post.getDeletedYn() == Status.Y){
            throw new IllegalArgumentException("이미 삭제되었습니다.");
        }
        post.setDeletedYn(Status.Y);
    }

}
