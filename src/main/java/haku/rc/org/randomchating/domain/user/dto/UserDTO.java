package haku.rc.org.randomchating.domain.user.dto; // 패키지 확인!

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDTO {
    private String loginId;   // 이메일 대신 아이디
    private String password;
    private String di;        // 프론트에서 인증 후 넘겨주는 암호화된 본인 값

    private String nickname;  // 랜챗 닉네임
    private int age;
    private String gender;
    private String introduction;
}