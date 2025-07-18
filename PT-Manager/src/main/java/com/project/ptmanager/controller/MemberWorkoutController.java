package com.project.ptmanager.controller;

import com.project.ptmanager.dto.WorkoutLogCreateRequest;
import com.project.ptmanager.dto.WorkoutLogDto;
import com.project.ptmanager.dto.WorkoutScheduleDto;
import com.project.ptmanager.service.MemberWorkoutLogService;
import com.project.ptmanager.service.MemberWorkoutScheduleService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberWorkoutController {

  private final MemberWorkoutScheduleService memberWorkoutScheduleService;
  private final MemberWorkoutLogService memberWorkoutLogService;

  @GetMapping("/schedules")
  public ResponseEntity<List<WorkoutScheduleDto>> getWorkoutScheduleList(
      @RequestParam int year,
      @RequestParam int month
  ) {

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현

    List<WorkoutScheduleDto> scheduleList = memberWorkoutScheduleService.getScheduleList(memberId,
        year,
        month);

    return ResponseEntity.ok().body(scheduleList);
  }

  @GetMapping("/schedule/{scheduleId}")
  public ResponseEntity<WorkoutScheduleDto> getWorkoutScheduleDetail(
      @PathVariable Long scheduleId
  ) {

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현

    WorkoutScheduleDto schedule = memberWorkoutScheduleService.getSchedule(memberId, scheduleId);

    return ResponseEntity.ok().body(schedule);
  }

  @GetMapping("/workoutLogs")
  public ResponseEntity<List<WorkoutLogDto>> getWorkoutLogList(
      @RequestParam int year,
      @RequestParam int month
  ) {

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현

    List<WorkoutLogDto> logList = memberWorkoutLogService.getWorkoutLogList(memberId, year, month);

    return ResponseEntity.ok().body(logList);
  }

  @GetMapping("/workoutLogs/{logId}")
  public ResponseEntity<WorkoutLogDto> getWorkoutLogDetail(
      @PathVariable Long logId
  ) {

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현

    WorkoutLogDto log = memberWorkoutLogService.getWorkoutLog(memberId, logId);

    return ResponseEntity.ok().body(log);
  }

  @PostMapping("/workoutLogs")
  public ResponseEntity<Long> createWorkoutLog(
      @RequestBody WorkoutLogCreateRequest request
  ) {

    LocalDate date = request.getDate();
    String exerciseList = request.getExerciseList();

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현
    Long trainerId = null; // TODO: 담당 트레이너가 있다면 불러오기

    Long logId = memberWorkoutLogService.createWorkoutLog(date, exerciseList, memberId,
        trainerId);

    return ResponseEntity.ok(logId);
  }
}
