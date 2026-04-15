package haku.rc.org.randomchating.domain.admin.repository;

import haku.rc.org.randomchating.domain.admin.entity.MatchExclusion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchExclusionRepository extends JpaRepository<MatchExclusion, Long> {
}