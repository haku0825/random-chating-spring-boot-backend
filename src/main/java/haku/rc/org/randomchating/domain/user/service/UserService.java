package haku.rc.org.randomchating.domain.user.service;

import haku.rc.org.randomchating.domain.user.dto.LoginDTO;
import haku.rc.org.randomchating.domain.user.dto.UserDTO;
import haku.rc.org.randomchating.domain.user.entity.User;
import haku.rc.org.randomchating.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
                .password(requestDto.getPassword()) // 일단 평문 저장 (나중에 암호화)
                .di(requestDto.getDi())
                .nickname(requestDto.getNickname())
                .age(requestDto.getAge())
                .gender(requestDto.getGender())
                .introduction(requestDto.getIntroduction())
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
    }

    // 2. 로그인
    @Transactional(readOnly = true)
    public void login(LoginDTO requestDto) {
        User user = userRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 로그인 성공 시 세션 처리나 토큰 발급은 나중에!
    }
}