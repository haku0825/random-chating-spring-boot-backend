package haku.rc.org.randomchating.domain.admin.entity;

import haku.rc.org.randomchating.domain.user.entity.User;
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
public class MatchExclusion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 차단 주체 (차단을 누른 사람)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 차단 대상 (다시는 만나기 싫은 사람)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excluded_user_id", nullable = false)
    private User excludedUser;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public MatchExclusion(User user, User excludedUser) {
        this.user = user;
        this.excludedUser = excludedUser;
    }
}