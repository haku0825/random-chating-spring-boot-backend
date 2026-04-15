package haku.rc.org.randomchating.domain.admin.repository;

import haku.rc.org.randomchating.domain.admin.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}