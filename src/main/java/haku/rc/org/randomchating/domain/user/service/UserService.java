package haku.rc.org.randomchating.domain.user.service;

import haku.rc.org.randomchating.domain.user.dto.LoginDTO;
import haku.rc.org.randomchating.domain.user.dto.UserDTO;
import haku.rc.org.randomchating.domain.user.entity.User;
import haku.rc.org.randomchating.domain.user.repository.UserRepository;
import haku.rc.org.randomchating.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 1. 회원가입
    @Transactional
    public void signup(UserDTO requestDto) {
        if (userRepository.existsByLoginId(requestDto.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (userRepository.existsByDi(requestDto.getDi())) {
            throw new IllegalArgumentException("이미 이 명의로 가입된 계정이 존재합니다.");
        }

        User user = User.builder()
                .loginId(requestDto.getLoginId())
                .password(passwordEncoder.encode(requestDto.getPassword())) // 🔒 해싱해서 저장
                .di(requestDto.getDi())
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(LoginDTO requestDto) {
        User user = userRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 틀렸습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) { // 🔒 해시값 비교
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return jwtTokenProvider.createToken(user.getLoginId(), user.getRole()); // 🎫 토큰 반환
    }
}