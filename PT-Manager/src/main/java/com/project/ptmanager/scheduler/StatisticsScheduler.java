package com.project.ptmanager.scheduler;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.service.StatisticsGeneratorService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsScheduler {

  private final MemberRepository memberRepository;
  private final StatisticsGeneratorService statisticsGeneratorService;

  // 매주 월요일 06:00에 지난 주간 통계 생성
  @Scheduled(cron = "0 0 6 * * MON")
  public void generateWeeklyStatisticsForAllMembers() {

    LocalDate lastWeekStart = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);

    List<Member> members = memberRepository.findAll();
    for (Member member : members) {
      statisticsGeneratorService.generateWeeklyStatistics(member.getId(), lastWeekStart);
    }
  }

  // 매월 1일 05:00에 지난 월간 통계 생성
  @Scheduled(cron = "0 0 5 1 * ?")
  public void generateMonthlyStatisticsForAllMembers() {

    LocalDate lastMonthStart = LocalDate.now().minusMonths(1).withDayOfMonth(1);
    List<Member> members = memberRepository.findAll();

    for (Member member : members) {
      statisticsGeneratorService.generateMonthlyStatistics(member.getId(), lastMonthStart);
    }
  }
} 