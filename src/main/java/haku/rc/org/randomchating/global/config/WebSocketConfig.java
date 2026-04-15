package haku.rc.org.randomchating.global.config;

import haku.rc.org.randomchating.global.util.interceptor.JwtHandshakeInterceptor;
import haku.rc.org.randomchating.global.util.interceptor.WebSocketJwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtHandshakeInterceptor handshakeInterceptor; // 방금 만든 문지기 소환!
    private final WebSocketJwtInterceptor webSocketJwtInterceptor; // 👈 방금 만든 2차 방어막 소환!

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 처음 연결을 시도할 주소 (예: ws://localhost:8080/ws-stomp)
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")
                .addInterceptors(handshakeInterceptor)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // 클라이언트가 메시지를 받을 때 (구독)
        registry.setApplicationDestinationPrefixes("/pub"); // 클라이언트가 메시지를 보낼 때 (발행)
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketJwtInterceptor);
    }
}
