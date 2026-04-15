package haku.rc.org.randomchating.domain.chat.repository;

import haku.rc.org.randomchating.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {}
