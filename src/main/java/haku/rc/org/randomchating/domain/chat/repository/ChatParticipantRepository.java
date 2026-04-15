package haku.rc.org.randomchating.domain.chat.repository;

import haku.rc.org.randomchating.domain.chat.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {}