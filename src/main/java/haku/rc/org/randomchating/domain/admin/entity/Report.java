package haku.rc.org.randomchating.domain.admin.entity;

import haku.rc.org.randomchating.domain.user.entity.User;
import haku.rc.org.randomchating.domain.chat.entity.ChatRoom;
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
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter; // 신고한 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id", nullable = false)
    private User reportedUser; // 신고 당한 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom; // 문제가 발생한 채팅방 (증거)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason; // 신고 사유

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Report(User reporter, User reportedUser, ChatRoom chatRoom, String reason) {
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.chatRoom = chatRoom;
        this.reason = reason;
    }

    public enum ReportStatus {
        PENDING,  // 관리자 확인 대기 중
        RESOLVED  // 처리 완료 (정지 등)
    }
}