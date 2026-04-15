package haku.rc.org.randomchating.domain.user.controller;

import haku.rc.org.randomchating.domain.user.dto.LoginDTO;
import haku.rc.org.randomchating.domain.user.dto.UserDTO;
import haku.rc.org.randomchating.domain.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO requestDto) {
        try {
            userService.signup(requestDto);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 로그인 API (JWT 발급 + 쿠키 설정)
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO requestDto, HttpServletResponse response) {
        try {
            // 1. 서비스에서 로그인 검증 및 토큰 생성
            String token = userService.login(requestDto);

            // 2. [하이브리드 보안] 웹소켓 핸드쉐이크용 HttpOnly 쿠키 설정
            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setHttpOnly(true);   // 자바스크립트 탈취 방지
            cookie.setPath("/");        // 모든 경로에서 쿠키 접근 가능
            cookie.setMaxAge(60 * 60 * 24); // 쿠키 수명 24시간
            // cookie.setSecure(true);  // HTTPS 환경이라면 활성화 추천
            response.addCookie(cookie);

            // 3. 응답 바디로 토큰 반환 (프론트엔드가 STOMP 헤더에 넣을 용도)
            return ResponseEntity.ok(token);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 내 정보 조회 API (인증 테스트용)
     * SecurityContext에 담긴 정보를 기반으로 동작합니다.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo() {
        // 실제로는 @AuthenticationPrincipal 등을 사용하여 현재 접속자를 식별합니다.
        // 현재는 간단하게 성공 메시지만 반환하여 인증 여부를 확인합니다.
        return ResponseEntity.ok("인증된 사용자입니다. 프로필 정보를 불러올 수 있습니다.");
    }
}