package trio.white.elephant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trio.white.elephant.domain.User;
import trio.white.elephant.dto.JoinDto;
import trio.white.elephant.dto.LoginDto;
import trio.white.elephant.dto.UserDto;
import trio.white.elephant.exception.UserEmailAlreadyExistException;
import trio.white.elephant.exception.UserNotFoundException;
import trio.white.elephant.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto findById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());

        return userDto;
    }

    @Transactional // 변경
    public void join(JoinDto joinDto) {

        validateDuplicateUser(joinDto);
        validatePassword(joinDto);

        User user = new User();
        user.setName(joinDto.getName());
        user.setEmail(joinDto.getEmail());
        user.setPassword(joinDto.getPassword());
        user.setPhone(joinDto.getPhone());

        userRepository.save(user);
    }

    // 중복 회원 검증
    private void validateDuplicateUser(JoinDto joinDto) {

        Optional<User> user = userRepository.findByEmail(joinDto.getEmail());
        if (user.isPresent()) {
            throw new UserEmailAlreadyExistException("이미 존재하는 이메일입니다. 새로운 이메일을 입력해주세요.");
        }
    }

    private void validatePassword(JoinDto joinDto) {

        if (!joinDto.getPassword().equals(joinDto.getAnotherPassword())) {
            throw new IllegalStateException("비밀번호가 다릅니다. 비밀번호를 다시 확인해주세요.");
        }
    }

    public UserDto login(LoginDto loginDto) {

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("1. 로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요."));
        if (!user.getPassword().equals(password)) {
            throw new UserNotFoundException("2. 로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.");
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        return userDto;
    }
}
