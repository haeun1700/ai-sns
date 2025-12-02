package ai_board.controller;

import ai_board.domain.user.UserDto;
import ai_board.domain.user.UserRequest;
import ai_board.domain.user.UsernameUpdateRequest;
import ai_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequest request) {
        UserDto createdUser = userService.create(request.username(), request.password());
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ [GET] 전체 회원 조회 (삭제되지 않은 유저만)
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ [PATCH] 회원 이름 수정
    @PatchMapping("/{id}/username")
    public ResponseEntity<UserDto> updateUsername(@PathVariable Long id,
                                                  @RequestBody UsernameUpdateRequest request) {
        // 요청 본문의 ID와 URL의 ID가 다르면 예외
        if (!id.equals(request.id())) {
            return ResponseEntity.badRequest().build();
        }

        UserDto updatedUser = userService.updateUser(request);
        return ResponseEntity.ok(updatedUser);
    }

    // ✅ [DELETE] 회원 삭제 (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
