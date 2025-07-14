package com.project.ptmanager.controller;

import com.project.ptmanager.dto.WorkoutLogCreateRequest;
import com.project.ptmanager.dto.WorkoutLogDto;
import com.project.ptmanager.dto.WorkoutScheduleDto;
import com.project.ptmanager.enums.WorkoutType;
import com.project.ptmanager.service.WorkoutLogService;
import com.project.ptmanager.service.WorkoutScheduleService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/workout")
public class MemberWorkoutController {

  private final WorkoutScheduleService workoutScheduleService;
  private final WorkoutLogService workoutLogService;

  @GetMapping("/schedule/list")
  public ResponseEntity<List<WorkoutScheduleDto>> getWorkoutScheduleList(
      @RequestParam int year,
      @RequestParam int month
  ) {

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현

    List<WorkoutScheduleDto> scheduleList = workoutScheduleService.getScheduleList(memberId, year,
        month);

    return ResponseEntity.ok().body(scheduleList);
  }

  @GetMapping("/schedule/detail")
  public ResponseEntity<WorkoutScheduleDto> getWorkoutScheduleDetail(
      @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date
  ) {

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현

    WorkoutScheduleDto schedule = workoutScheduleService.getSchedule(memberId, date);

    return ResponseEntity.ok().body(schedule);
  }

  @GetMapping("/log/list")
  public ResponseEntity<List<WorkoutLogDto>> getWorkoutLogList(
      @RequestParam int year,
      @RequestParam int month
  ) {

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현

    List<WorkoutLogDto> logList = workoutLogService.getWorkoutLogList(memberId, year, month);

    return ResponseEntity.ok().body(logList);
  }

  @GetMapping("/log/detail")
  public ResponseEntity<WorkoutLogDto> getWorkoutLogDetail(
      @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date
  ) {

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현

    WorkoutLogDto log = workoutLogService.getWorkoutLog(memberId, date);

    return ResponseEntity.ok().body(log);
  }

  @PostMapping("/log/create")
  public ResponseEntity<Long> createWorkoutLog(
      @RequestBody WorkoutLogCreateRequest request
  ) {

    LocalDate date = request.getDate();
    String exerciseList = request.getExerciseList();

    Long memberId = 0L; // TODO: 인증에서 멤버 키 가져오기 구현
    Long trainerId = null; // TODO: 담당 트레이너가 있다면 불러오기

    Long logId = workoutLogService.createWorkoutLog(date, WorkoutType.SELF, exerciseList, memberId,
        trainerId);

    return ResponseEntity.ok(logId);
  }
}
