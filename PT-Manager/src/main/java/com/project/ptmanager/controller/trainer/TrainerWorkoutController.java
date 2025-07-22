package com.project.ptmanager.controller.trainer;

import com.project.ptmanager.dto.workout.WorkoutFeedbackRequestDto;
import com.project.ptmanager.dto.workout.WorkoutFeedbackResponseDto;
import com.project.ptmanager.dto.workout.WorkoutLogCreateRequestDto;
import com.project.ptmanager.dto.workout.WorkoutLogDto;
import com.project.ptmanager.dto.workout.WorkoutScheduleCreateRequestDto;
import com.project.ptmanager.dto.workout.WorkoutScheduleDto;
import com.project.ptmanager.security.CustomUserDetails;
import com.project.ptmanager.service.TrainerWorkoutFeedbackService;
import com.project.ptmanager.service.TrainerWorkoutLogService;
import com.project.ptmanager.service.TrainerWorkoutScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('TRAINER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerWorkoutController {

  private final TrainerWorkoutScheduleService trainerWorkoutScheduleService;
  private final TrainerWorkoutLogService trainerWorkoutLogService;
  private final TrainerWorkoutFeedbackService trainerWorkoutFeedbackService;

  @GetMapping("/members/{memberId}/schedules")
  public ResponseEntity<List<WorkoutScheduleDto>> getMemberScheduleList(
      @PathVariable("memberId") Long memberId,
      @RequestParam int year,
      @RequestParam int month,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    List<WorkoutScheduleDto> list = trainerWorkoutScheduleService.getScheduleList(
        trainerId, memberId, year, month);

    return ResponseEntity.ok(list);
  }

  @GetMapping("/members/{memberId}/schedules/{scheduleId}")
  public ResponseEntity<WorkoutScheduleDto> getMemberScheduleDetail(
      @PathVariable Long memberId,
      @PathVariable Long scheduleId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    WorkoutScheduleDto schedule = trainerWorkoutScheduleService.getSchedule(trainerId, memberId,
        scheduleId);

    return ResponseEntity.ok(schedule);
  }

  @PostMapping("/members/{memberId}/schedules")
  public ResponseEntity<Long> createMemberSchedule(
      @PathVariable Long memberId,
      @RequestBody WorkoutScheduleCreateRequestDto request,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    Long scheduleId = trainerWorkoutScheduleService.createSchedule(trainerId, memberId, request);

    return ResponseEntity.ok(scheduleId);
  }

  @GetMapping("/members/{memberId}/workoutlogs")
  public ResponseEntity<List<WorkoutLogDto>> getMemberWorkoutLogs(
      @PathVariable Long memberId,
      @RequestParam int year,
      @RequestParam int month,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    List<WorkoutLogDto> list = trainerWorkoutLogService.getWorkoutLogList(trainerId,
        memberId, year, month);

    return ResponseEntity.ok(list);
  }

  @GetMapping("/members/{memberId}/workoutlogs/{logId}")
  public ResponseEntity<WorkoutLogDto> getMemberWorkoutLog(
      @PathVariable Long memberId,
      @PathVariable Long logId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    WorkoutLogDto workoutLog = trainerWorkoutLogService.getWorkoutLog(trainerId, memberId, logId);

    return ResponseEntity.ok(workoutLog);
  }

  @PostMapping("/members/{memberId}/workoutlogs")
  public ResponseEntity<Long> createMemberWorkoutLog(
      @PathVariable Long memberId,
      @RequestBody WorkoutLogCreateRequestDto request,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    Long logId = trainerWorkoutLogService.createLog(trainerId, memberId, request);

    return ResponseEntity.ok(logId);
  }

  @GetMapping("/members/{memberId}/workoutlogs/{logId}/feedbacks")
  public ResponseEntity<List<WorkoutFeedbackResponseDto>> getMemberWorkoutFeedbacks(
      @PathVariable Long memberId,
      @PathVariable Long logId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    List<WorkoutFeedbackResponseDto> list = trainerWorkoutFeedbackService.getFeedbackList(trainerId,
        memberId, logId);

    return ResponseEntity.ok(list);
  }

  @PostMapping("/members/{memberId}/workoutlogs/{logId}/feedbacks")
  public ResponseEntity<Long> createMemberWorkoutFeedback(
      @PathVariable Long memberId,
      @PathVariable Long logId,
      @RequestBody WorkoutFeedbackRequestDto request,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    Long feedbackId = trainerWorkoutFeedbackService.createFeedback(trainerId, memberId, logId,
        request);

    return ResponseEntity.ok(feedbackId);
  }
}