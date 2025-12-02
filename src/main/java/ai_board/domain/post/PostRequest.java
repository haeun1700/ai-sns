package ai_board.domain.post;


public record PostRequest(Long userId, String title, String content) {
}
