package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.enums.AccessLevel;
import ru.devpro.model.Report;

import java.util.List;
@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {
    List<Report> findByAccessLevelIn(List<AccessLevel> list);
}
