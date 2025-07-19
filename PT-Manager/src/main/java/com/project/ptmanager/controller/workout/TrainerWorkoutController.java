package com.project.ptmanager.controller.workout;

import com.project.ptmanager.dto.WorkoutScheduleCreateRequest;
import com.project.ptmanager.dto.WorkoutLogCreateRequest;
import com.project.ptmanager.dto.WorkoutLogDto;
import com.project.ptmanager.dto.WorkoutScheduleDto;
import com.project.ptmanager.service.TrainerWorkoutLogService;
import com.project.ptmanager.service.TrainerWorkoutScheduleService;
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
@RequestMapping("/trainer")
public class TrainerWorkoutController {

  private final TrainerWorkoutScheduleService trainerWorkoutScheduleService;
  private final TrainerWorkoutLogService trainerWorkoutLogService;

  @GetMapping("/myMember/{memberId}/schedules")
  public ResponseEntity<List<WorkoutScheduleDto>> getMemberScheduleList(
      @PathVariable("memberId") Long memberId,
      @RequestParam int year,
      @RequestParam int month
  ) {

    Long trainerId = null; // TODO: 인증에서 아이디 가져오기

    List<WorkoutScheduleDto> list = trainerWorkoutScheduleService.getScheduleList(
        trainerId, memberId, year, month);

    return ResponseEntity.ok(list);
  }

  @GetMapping("/myMember/{memberId}/schedules/{scheduleId}")
  public ResponseEntity<WorkoutScheduleDto> getMemberScheduleDetail(
      @PathVariable Long memberId,
      @PathVariable Long scheduleId
  ) {

    Long trainerId = null; // TODO: 인증에서 아이디 가져오기

    WorkoutScheduleDto schedule = trainerWorkoutScheduleService.getSchedule(trainerId, memberId,
        scheduleId);

    return ResponseEntity.ok(schedule);
  }

  @PostMapping("/myMember/{memberId}/schedules")
  public ResponseEntity<Long> createMemberSchedule(
      @PathVariable Long memberId,
      @RequestBody WorkoutScheduleCreateRequest request
  ) {

    Long trainerId = null; // TODO: 인증에서 아이디 가져오기

    Long scheduleId = trainerWorkoutScheduleService.createSchedule(trainerId, memberId, request);

    return ResponseEntity.ok(scheduleId);
  }

  @GetMapping("/myMember/{memberId}/workoutLogs")
  public ResponseEntity<List<WorkoutLogDto>> getMemberWorkoutLogs(
      @PathVariable Long memberId,
      @RequestParam int year,
      @RequestParam int month
  ) {

    Long trainerId = null; // TODO: 인증에서 아이디 가져오기

    List<WorkoutLogDto> list = trainerWorkoutLogService.getWorkoutLogList(trainerId,
        memberId, year, month);

    return ResponseEntity.ok(list);
  }

  @GetMapping("/myMember/{memberId}/workoutLogs/{logId}")
  public ResponseEntity<WorkoutLogDto> getMemberWorkoutLog(
      @PathVariable Long memberId,
      @PathVariable Long logId
  ) {

    Long trainerId = null; // TODO: 인증에서 아이디 가져오기

    WorkoutLogDto workoutLog = trainerWorkoutLogService.getWorkoutLog(trainerId, memberId, logId);

    return ResponseEntity.ok(workoutLog);
  }

  @PostMapping("/myMember/{memberId}/workoutLogs")
  public ResponseEntity<Long> createMemberWorkoutLog(
      @PathVariable Long memberId,
      @RequestBody WorkoutLogCreateRequest request
  ) {

    Long trainerId = null; // TODO: 인증에서 아이디 가져오기

    Long logId = trainerWorkoutLogService.createLog(trainerId, memberId, request);

    return ResponseEntity.ok(logId);
  }
}