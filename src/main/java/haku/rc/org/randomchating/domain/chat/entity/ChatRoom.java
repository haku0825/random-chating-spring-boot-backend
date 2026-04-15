package haku.rc.org.randomchating.domain.chat.entity;

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
@EntityListeners(AuditingEntityListener.class) // 생성 시간 자동 기록용
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 방이 활성화 상태인지(채팅 중인지), 종료된 상태인지 구분
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.ACTIVE;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 방 생성 시간

    @Builder
    public ChatRoom(RoomStatus status) {
        this.status = status != null ? status : RoomStatus.ACTIVE;
    }

    public enum RoomStatus {
        ACTIVE,     // 채팅 진행 중
        CLOSED      // 누군가 나가서 종료됨
    }
}