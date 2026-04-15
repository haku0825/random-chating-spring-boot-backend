package haku.rc.org.randomchating.domain.user.repository;

import haku.rc.org.randomchating.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 1. 아이디 중복 가입 방지용
    boolean existsByLoginId(String loginId);

    // 2. 🚨 실명인증(DI) 기반 1인 1계정 중복 가입 방지용
    boolean existsByDi(String di);

    // 3. 로그인 시 아이디로 유저 찾기용
    Optional<User> findByLoginId(String loginId);
}