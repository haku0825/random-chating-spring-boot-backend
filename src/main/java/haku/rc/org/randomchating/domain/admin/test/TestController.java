package haku.rc.org.randomchating.domain.admin.test;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String TEST_QUEUE = "test_queue";

    // 1. 화면 띄우기 (타임리프)
    @GetMapping("/test/queue")
    public String testPage() {
        return "test-queue"; // test-queue.html 파일을 찾아서 띄워줌
    }

    // 2. 큐에 숫자 넣기 (전송 버튼)
    @ResponseBody
    @PostMapping("/api/test/enqueue")
    public List<Object> enqueue(@RequestParam String number) {
        // 큐의 맨 뒤(오른쪽)에 숫자 추가
        redisTemplate.opsForList().rightPush(TEST_QUEUE, number);
        // 현재 큐의 전체 목록 반환
        return redisTemplate.opsForList().range(TEST_QUEUE, 0, -1);
    }

    // 3. 랜덤 뽑기 및 매칭 확인
    @ResponseBody
    @PostMapping("/api/test/draw")
    public Map<String, Object> draw() {
        Map<String, Object> result = new HashMap<>();

        // 1~5 사이의 랜덤 숫자 뽑기
        int randomNum = new Random().nextInt(5) + 1;
        String drawStr = String.valueOf(randomNum);
        result.put("drawNumber", drawStr);

        // 큐의 맨 앞(인덱스 0) 사람 확인 (빼지는 않고 보기만 함)
        String firstInQueue = (String) redisTemplate.opsForList().index(TEST_QUEUE, 0);

        if (firstInQueue == null) {
            result.put("status", "empty");
            result.put("message", "대기열이 비어있습니다!");
        } else if (drawStr.equals(firstInQueue)) {
            // 🎉 매칭 성공! 큐의 맨 앞에서 진짜로 빼냄 (Pop)
            redisTemplate.opsForList().leftPop(TEST_QUEUE);
            result.put("status", "success");
            result.put("message", "매칭 성공! (" + drawStr + "번 대기자 입장)");
        } else {
            // ❌ 불일치
            result.put("status", "fail");
            result.put("message", "불일치! (맨 앞은 " + firstInQueue + "번)");
        }

        // 변동된 최신 큐 상태를 함께 반환
        result.put("queue", redisTemplate.opsForList().range(TEST_QUEUE, 0, -1));
        return result;
    }

    // 4. (보너스) 큐 초기화 버튼용
    @ResponseBody
    @PostMapping("/api/test/clear")
    public List<Object> clear() {
        redisTemplate.delete(TEST_QUEUE);
        return redisTemplate.opsForList().range(TEST_QUEUE, 0, -1);
    }
}