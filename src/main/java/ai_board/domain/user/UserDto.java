package ai_board.domain.user;


import java.time.ZonedDateTime;

public record UserDto(
        Long userId,
        String username,
        ZonedDateTime createDateTime,
        ZonedDateTime updateDateTime
) {
    public static UserDto from(UserEntity user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
