package haku.rc.org.randomchating.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDTO {
    private String loginId;
    private String password;
}