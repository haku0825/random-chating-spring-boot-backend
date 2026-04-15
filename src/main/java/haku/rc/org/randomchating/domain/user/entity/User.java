package haku.rc.org.randomchating.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users") // DB에서 user는 예약어라 users로 안전하게 사용
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- 기존 로그인/인증 정보 ---
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // --- 🚀 추가된 랜덤채팅 프로필 정보 ---
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender; // "MALE" 또는 "FEMALE" (대기열 분리 핵심 키)

    @Column(columnDefinition = "TEXT")
    private String introduction; // 자기소개 (하고 싶은 게임, 취향 등 자유형식)

    private String profileImageUrl; // 프로필 사진 URL (초기엔 없을 수 있으므로 nullable)

    @Column(nullable = false)
    private int coin = 0; // 가입 시 초기 코인 0개 세팅

    @Column(nullable = false)
    private boolean is_premium = false;

    // --- 🚨 관리자 운영용 상태값 ---
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Builder
    public User(String email, String password, String role, String nickname, int age, String gender, String introduction, String profileImageUrl) {
        this.email = email;
        this.password = password;
        this.role = role != null ? role : "ROLE_USER";
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.introduction = introduction;
        this.profileImageUrl = profileImageUrl;
        // status와 coin은 객체 생성 시 기본값(ACTIVE, 0)이 들어가므로 파라미터에서 제외
    }

    // 유저 상태를 관리할 Enum 클래스
    public enum UserStatus {
        ACTIVE,      // 정상 활동 중
        SUSPENDED    // 관리자에 의해 정지됨
    }
}