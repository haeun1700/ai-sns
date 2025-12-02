package ai_board.domain.post;


import java.time.ZonedDateTime;

public record PostDto(Long id, String title, String content, ZonedDateTime createdAt, ZonedDateTime updatedAt ) {
    public static PostDto from(PostEntity post) {
        return new PostDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
