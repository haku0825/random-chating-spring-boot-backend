package haku.rc.org.randomchating.global.util.interceptor;

import haku.rc.org.randomchating.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketJwtInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // STOMP 통신에서 채팅방에 입장(CONNECT)하려고 할 때 2차 검사!
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 자바스크립트가 STOMP 헤더에 넣어 보낸 토큰 꺼내기
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                if (!jwtTokenProvider.validateToken(token)) {
                    throw new IllegalArgumentException("STOMP 헤더: 유효하지 않은 토큰입니다.");
                }
            } else {
                throw new IllegalArgumentException("STOMP 헤더: 인증 토큰이 없습니다.");
            }
        }
        return message;
    }
}