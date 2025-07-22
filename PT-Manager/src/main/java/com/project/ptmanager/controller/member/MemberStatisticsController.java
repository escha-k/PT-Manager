package com.project.ptmanager.controller.member;

import com.project.ptmanager.dto.statistics.StatisticsDto;
import com.project.ptmanager.security.CustomUserDetails;
import com.project.ptmanager.service.statistics.StatisticsService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MEMBER')")
public class MemberStatisticsController {

  private final StatisticsService statisticsService;

  @GetMapping("/weekly")
  public ResponseEntity<StatisticsDto> getWeeklyStatistics(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStart,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    StatisticsDto weeklyStatistics = statisticsService.getWeeklyStatistics(memberId, weekStart);

    return ResponseEntity.ok(weeklyStatistics);
  }

  @GetMapping("/monthly")
  public ResponseEntity<StatisticsDto> getMonthlyStatistics(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate monthStart,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    StatisticsDto monthlyStatistics = statisticsService.getMonthlyStatistics(memberId, monthStart);

    return ResponseEntity.ok(monthlyStatistics);
  }

  @GetMapping("/weekly/range")
  public ResponseEntity<List<StatisticsDto>> getWeeklyStatisticsInRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    List<StatisticsDto> weeklyStatisticsInRange = statisticsService.getWeeklyStatisticsInRange(
        memberId, start, end);

    return ResponseEntity.ok(weeklyStatisticsInRange);
  }

  @GetMapping("/monthly/range")
  public ResponseEntity<List<StatisticsDto>> getMonthlyStatisticsInRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    List<StatisticsDto> monthlyStatisticsInRange = statisticsService.getMonthlyStatisticsInRange(
        memberId, start, end);

    return ResponseEntity.ok(monthlyStatisticsInRange);
  }
} 