package haku.rc.org.randomchating.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- 🔑 로그인 및 인증 정보 ---
    @Column(nullable = false, unique = true)
    private String loginId; // 이메일 대신 사용할 로그인 아이디

    @Column(nullable = false)
    private String password;

    // 실명인증 고유식별자 (이 값이 같으면 동일 인물)
    @Column(nullable = false, unique = true)
    private String di;

    @Column(nullable = false)
    private String role;

    // --- 🚀 랜덤채팅 프로필 정보 ---
    @Column(nullable = false)
    private String nickname; // 랜챗에서 사용할 닉네임

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private String profileImageUrl;

    @Column(nullable = false)
    private int coin = 0;

    @Column(nullable = false)
    private boolean is_premium = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Builder
    public User(String loginId, String password, String di, String role, String nickname, int age, Gender gender, String introduction, String profileImageUrl) {
        this.loginId = loginId;
        this.password = password;
        this.di = di;
        this.role = role != null ? role : "ROLE_USER";
        this.nickname = nickname;
        this.age = age;
        this.gender = gender; // 변경됨        this.introduction = introduction;
        this.profileImageUrl = profileImageUrl;
    }

    public enum UserStatus {
        ACTIVE,
        SUSPENDED
    }

    public enum Gender {
        MALE, FEMALE
    }

    public void useCoin(int amount) {
        if (this.coin < amount) {
            throw new IllegalArgumentException("코인이 부족합니다.");
        }
        this.coin -= amount;
    }

    public void addCoin(int amount) {
        this.coin += amount;
    }
}