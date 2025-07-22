package com.project.ptmanager.repository.statistics;

import com.project.ptmanager.domain.statistics.Statistics;
import com.project.ptmanager.enums.StatisticsType;
import com.project.ptmanager.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Optional<Statistics> findByMemberAndTypeAndStartDate(Member member, StatisticsType type, LocalDate startDate);
    List<Statistics> findAllByMemberAndTypeAndStartDateBetween(Member member, StatisticsType type, LocalDate start, LocalDate end);
} 