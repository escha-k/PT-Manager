package com.project.ptmanager.service;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.statistics.Statistics;
import com.project.ptmanager.dto.statistics.StatisticsDto;
import com.project.ptmanager.enums.StatisticsType;
import com.project.ptmanager.exception.MemberNotFoundException;
import com.project.ptmanager.exception.StatisticsNotFoundException;
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

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException("회원 정보를 찾을 수 없습니다."));

    Statistics statistics = statisticsRepository.findByMemberAndTypeAndStartDate(
        member,
        StatisticsType.WEEKLY,
        weekStart
    ).orElseThrow(() -> new StatisticsNotFoundException("해당 기간에 생성된 주간 통계가 없습니다."));

    return StatisticsDto.fromEntity(statistics);
  }

  @Cacheable(value = "monthlyStatistics", key = "#memberId + ':' + #monthStart")
  public StatisticsDto getMonthlyStatistics(Long memberId, LocalDate monthStart) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException("회원 정보를 찾을 수 없습니다."));

    Statistics statistics = statisticsRepository.findByMemberAndTypeAndStartDate(
        member,
        StatisticsType.MONTHLY,
        monthStart
    ).orElseThrow(() -> new StatisticsNotFoundException("해당 기간에 생성된 월간 통계가 없습니다."));

    return StatisticsDto.fromEntity(statistics);
  }

  public List<StatisticsDto> getWeeklyStatisticsInRange(Long memberId, LocalDate start,
      LocalDate end) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException("회원 정보를 찾을 수 없습니다."));

    List<Statistics> list = statisticsRepository.findAllByMemberAndTypeAndStartDateBetween(
        member, StatisticsType.WEEKLY, start, end);

    if (list.isEmpty()) {
      throw new StatisticsNotFoundException("해당 기간에 생성된 주간 통계가 없습니다.");
    }

    return list.stream().map(StatisticsDto::fromEntity).toList();
  }

  public List<StatisticsDto> getMonthlyStatisticsInRange(Long memberId, LocalDate start,
      LocalDate end) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException("회원 정보를 찾을 수 없습니다."));

    List<Statistics> list = statisticsRepository.findAllByMemberAndTypeAndStartDateBetween(
        member, StatisticsType.MONTHLY, start, end);

    if (list.isEmpty()) {
      throw new StatisticsNotFoundException("해당 기간에 생성된 월간 통계가 없습니다.");
    }

    return list.stream().map(StatisticsDto::fromEntity).toList();
  }
}