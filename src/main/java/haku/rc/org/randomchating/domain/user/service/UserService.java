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
        // 1. 아이디 확인
        User user = userRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 2. 비밀번호 확인 (BCrypt 사용)
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. JWT 토큰 발행 (유저의 아이디와 역할을 담음)
        return jwtTokenProvider.createToken(user.getLoginId(), user.getRole());
    }
}