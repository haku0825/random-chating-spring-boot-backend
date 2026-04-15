package haku.rc.org.randomchating.domain.user.repository;

import haku.rc.org.randomchating.domain.user.entity.CoinHistory;
import haku.rc.org.randomchating.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinHistoryRepository extends JpaRepository<CoinHistory, Long> {

    // 특정 유저의 코인 내역을 최신순(내림차순)으로 모두 가져오기 (나중에 '내 지갑' 화면 만들 때 씁니다)
    List<CoinHistory> findAllByUserOrderByCreatedAtDesc(User user);
}