package haku.rc.org.randomchating.global.util.interceptor;

import haku.rc.org.randomchating.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest req = servletRequest.getServletRequest();
            Cookie[] cookies = req.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    // 쿠키에 우리가 구워준 jwt_token이 있는지 확인
                    if ("jwt_token".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        // 토큰이 유효하면 파이프 생성을 허락함!
                        return jwtTokenProvider.validateToken(token);
                    }
                }
            }
        }
        System.out.println("🚨 쿠키 인증 실패: 핸드쉐이크 거부됨");
        return false; // 쿠키가 없거나 유효하지 않으면 파이프 생성 거부 (입구 컷)
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 통과 후 처리할 일 (생략 가능)
    }
}