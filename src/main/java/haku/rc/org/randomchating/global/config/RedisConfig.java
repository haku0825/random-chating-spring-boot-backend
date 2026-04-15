package haku.rc.org.randomchating.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // application.properties에 적어둔 host와 port 값을 가져옵니다.
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    /**
     * 1. Redis 연결 공장 (ConnectionFactory) 생성
     * 스프링 부트 2.0부터는 Jedis 대신 성능이 더 좋은 Lettuce를 기본으로 사용합니다.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    /**
     * 2. RedisTemplate 세팅 (실제로 우리가 로직 짤 때 사용할 핵심 도구!)
     * * 💡 [중요] 직렬화(Serializer) 설정을 안 하면 Redis CLI나 관리 툴에서 데이터를 볼 때
     * "\xac\xed\x00\x05t\x00\x04test" 같이 이상한 외계어로 깨져서 보입니다.
     * 이를 사람이 읽을 수 있는 문자열과 JSON 형태로 바꿔주는 설정입니다.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // Key 값은 String 타입으로 명확하게 저장
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // Value 값도 String 형태로 통일! (어차피 유저 ID나 상태값 등 문자열만 저장할 예정)
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // Hash 자료구조를 쓸 때도 동일하게 적용
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}