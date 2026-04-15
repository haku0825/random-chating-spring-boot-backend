package haku.rc.org.randomchating.domain.user.repository;

import haku.rc.org.randomchating.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 1. 회원가입 시 이메일 중복 체크용
    boolean existsByEmail(String email);

    // 2. 로그인 시 이메일로 유저 정보를 찾을 때 사용
    Optional<User> findByEmail(String email);
}