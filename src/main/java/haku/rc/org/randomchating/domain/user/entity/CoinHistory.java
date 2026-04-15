package haku.rc.org.randomchating.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CoinHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 누구의 코인 내역인가?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int changeAmount; // 코인 변화량 (예: 매칭 차감은 -1, 충전은 +10)

    @Column(nullable = false)
    private int remainingAmount; // 변화 후 남은 잔액 (추적/검증용)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    private String description; // 상세 내역 (예: "매칭 성사 차감", "회원가입 축하금")

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 거래 일시

    @Builder
    public CoinHistory(User user, int changeAmount, int remainingAmount, TransactionType type, String description) {
        this.user = user;
        this.changeAmount = changeAmount;
        this.remainingAmount = remainingAmount;
        this.type = type;
        this.description = description;
    }

    public enum TransactionType {
        RECHARGE, // 결제 충전
        USE,      // 매칭 등에 사용
        REFUND,   // 환불 처리
        ADMIN     // 관리자가 수동으로 지급/차감 (테스트용으로 쓰기 좋음!)
    }
}