package ai_board.service;

import ai_board.domain.global.Status;
import ai_board.domain.user.UserDto;
import ai_board.domain.user.UserEntity;
import ai_board.domain.user.UsernameUpdateRequest;
import ai_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // c, 생성
    public UserDto create(String username, String password) {
        validateDuplicateUser(username);
        var userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userRepository.save(userEntity);

        return UserDto.from(userEntity);
    }

    // 중복 조회
    private void validateDuplicateUser(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }
    // r, 회원 단건 조회, 존재 안할 수 도 있어서 optional처리
    public Optional<UserDto> getUser(Long id) {
       return userRepository.findById(id)
               .map(UserDto::from);
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    // 전체 조회
    public List<UserDto> getAllUsers() {
        return userRepository.findAllByDeletedYn(Status.N)
                .stream().map(UserDto::from).toList();
    }
    // 회원 정보 업데이트
    public UserDto updateUser(UsernameUpdateRequest request) {
        UserEntity user = userRepository.findById(request.id())
                        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        validateDuplicateUser(request.username());
        user.setUsername(request.username());
        return UserDto.from(user);
    }
    // d, 회원 삭제
    public void deleteUser(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (user.getDeletedYn() == Status.Y){
            throw new IllegalArgumentException("이미 삭제된 사용자입니다.");
        }
        user.setDeletedYn(Status.Y);
    }
}
