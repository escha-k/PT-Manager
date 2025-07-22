package com.project.ptmanager.service;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.statistics.Statistics;
import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.enums.StatisticsType;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.statistics.StatisticsRepository;
import com.project.ptmanager.repository.workout.WorkoutLogRepository;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsGeneratorService {

  private final WorkoutLogRepository workoutLogRepository;
  private final StatisticsRepository statisticsRepository;
  private final MemberRepository memberRepository;
  private final CacheManager cacheManager;

  public void generateWeeklyStatistics(Long memberId, LocalDate weekStart) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);
    LocalDate weekEnd = weekStart.plusDays(6);

    Statistics statistics = generateStatistics(member, weekStart, weekEnd, StatisticsType.WEEKLY);
    Statistics saved = saveStatistics(statistics);

    // 저장 후 캐싱
    cacheManager.getCache("weeklyStatistics").put(memberId + ":" + weekStart, saved);
  }

  public void generateMonthlyStatistics(Long memberId, LocalDate monthStart) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);
    LocalDate monthEnd = monthStart.with(TemporalAdjusters.lastDayOfMonth());

    Statistics statistics = generateStatistics(member, monthStart, monthEnd,
        StatisticsType.MONTHLY);
    Statistics saved = saveStatistics(statistics);

    // 저장 후 캐싱
    cacheManager.getCache("monthlyStatistics").put(memberId + ":" + monthStart, saved);
  }

  private Statistics generateStatistics(Member member, LocalDate start, LocalDate end,
      StatisticsType type) {

    List<WorkoutLog> logs = workoutLogRepository.findAllByMemberIdAndDateBetween(member.getId(),
        start, end);

    // 출석률 계산
    long attendance = logs.stream().map(WorkoutLog::getDate).distinct().count();
    long totalDays = start.until(end).getDays() + 1;
    double attendanceRate = totalDays > 0 ? (double) attendance / totalDays : 0.0;

    // 총 중량 계산
    double totalWeight = logs.stream()
        .flatMap(log -> log.getExerciseList() != null ? log.getExerciseList().stream()
            : java.util.stream.Stream.empty())
        .mapToDouble(w -> w.getSets() * w.getReplays() * w.getWeight())
        .sum();

    return Statistics.builder()
        .type(type)
        .startDate(start)
        .attendance(attendanceRate)
        .totalWeight(totalWeight)
        .member(member)
        .build();
  }

  private Statistics saveStatistics(Statistics statistics) {

    return statisticsRepository.findByMemberAndTypeAndStartDate(
            statistics.getMember(), statistics.getType(), statistics.getStartDate())
        .map(existing -> {
          existing.setAttendance(statistics.getAttendance());
          existing.setTotalWeight(statistics.getTotalWeight());
          return statisticsRepository.save(existing);
        })
        .orElseGet(() -> statisticsRepository.save(statistics));
  }
} 