package haku.rc.org.randomchating.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDTO {
    private String loginId;
    private String password;
    private String di; // 실명인증 고유값
    private String nickname;
    private int age;
    private String gender; // "MALE" or "FEMALE"
    private String introduction;
}