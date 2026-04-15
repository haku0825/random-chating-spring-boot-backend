package haku.rc.org.randomchating.domain.chat.repository;

import haku.rc.org.randomchating.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {}