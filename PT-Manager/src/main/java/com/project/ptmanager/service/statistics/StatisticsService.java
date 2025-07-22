package com.project.ptmanager.service.statistics;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.statistics.Statistics;
import com.project.ptmanager.dto.statistics.StatisticsDto;
import com.project.ptmanager.enums.StatisticsType;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.exception.impl.StatisticsNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.statistics.StatisticsRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final MemberRepository memberRepository;
  private final StatisticsRepository statisticsRepository;

  @Cacheable(value = "weeklyStatistics", key = "#memberId + ':' + #weekStart")
  public StatisticsDto getWeeklyStatistics(Long memberId, LocalDate weekStart) {

    Member member = getMember(memberId);

    Statistics statistics = statisticsRepository.findByMemberAndTypeAndStartDate(
        member,
        StatisticsType.WEEKLY,
        weekStart
    ).orElseThrow(StatisticsNotFoundException::new);

    return StatisticsDto.fromEntity(statistics);
  }

  @Cacheable(value = "monthlyStatistics", key = "#memberId + ':' + #monthStart")
  public StatisticsDto getMonthlyStatistics(Long memberId, LocalDate monthStart) {

    Member member = getMember(memberId);

    Statistics statistics = statisticsRepository.findByMemberAndTypeAndStartDate(
        member,
        StatisticsType.MONTHLY,
        monthStart
    ).orElseThrow(StatisticsNotFoundException::new);

    return StatisticsDto.fromEntity(statistics);
  }

  public List<StatisticsDto> getWeeklyStatisticsInRange(Long memberId, LocalDate start,
      LocalDate end) {

    Member member = getMember(memberId);

    List<Statistics> list = statisticsRepository.findAllByMemberAndTypeAndStartDateBetween(
        member, StatisticsType.WEEKLY, start, end);

    if (list.isEmpty()) {
      throw new StatisticsNotFoundException();
    }

    return list.stream().map(StatisticsDto::fromEntity).toList();
  }

  public List<StatisticsDto> getMonthlyStatisticsInRange(Long memberId, LocalDate start,
      LocalDate end) {

    Member member = getMember(memberId);

    List<Statistics> list = statisticsRepository.findAllByMemberAndTypeAndStartDateBetween(
        member, StatisticsType.MONTHLY, start, end);

    if (list.isEmpty()) {
      throw new StatisticsNotFoundException();
    }

    return list.stream().map(StatisticsDto::fromEntity).toList();
  }

  private Member getMember(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);
  }
}