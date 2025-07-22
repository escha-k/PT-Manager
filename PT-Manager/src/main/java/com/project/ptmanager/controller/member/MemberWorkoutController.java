package com.project.ptmanager.controller.member;

import com.project.ptmanager.domain.workout.model.Workout;
import com.project.ptmanager.dto.workout.WorkoutFeedbackResponseDto;
import com.project.ptmanager.dto.workout.WorkoutLogCreateRequestDto;
import com.project.ptmanager.dto.workout.WorkoutLogDto;
import com.project.ptmanager.dto.workout.WorkoutScheduleDto;
import com.project.ptmanager.security.CustomUserDetails;
import com.project.ptmanager.service.MemberWorkoutFeedbackService;
import com.project.ptmanager.service.MemberWorkoutLogService;
import com.project.ptmanager.service.MemberWorkoutScheduleService;
import java.time.LocalDate;
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

@PreAuthorize("hasRole('MEMBER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberWorkoutController {

  private final MemberWorkoutScheduleService memberWorkoutScheduleService;
  private final MemberWorkoutLogService memberWorkoutLogService;
  private final MemberWorkoutFeedbackService memberWorkoutFeedbackService;

  @GetMapping("/schedules")
  public ResponseEntity<List<WorkoutScheduleDto>> getWorkoutScheduleList(
      @RequestParam int year,
      @RequestParam int month,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    List<WorkoutScheduleDto> scheduleList = memberWorkoutScheduleService.getScheduleList(memberId,
        year,
        month);

    return ResponseEntity.ok().body(scheduleList);
  }

  @GetMapping("/schedule/{scheduleId}")
  public ResponseEntity<WorkoutScheduleDto> getWorkoutScheduleDetail(
      @PathVariable Long scheduleId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    WorkoutScheduleDto schedule = memberWorkoutScheduleService.getSchedule(memberId, scheduleId);

    return ResponseEntity.ok().body(schedule);
  }

  @GetMapping("/workoutlogs")
  public ResponseEntity<List<WorkoutLogDto>> getWorkoutLogList(
      @RequestParam int year,
      @RequestParam int month,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    List<WorkoutLogDto> logList = memberWorkoutLogService.getWorkoutLogList(memberId, year, month);

    return ResponseEntity.ok().body(logList);
  }

  @GetMapping("/workoutlogs/{logId}")
  public ResponseEntity<WorkoutLogDto> getWorkoutLogDetail(
      @PathVariable Long logId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    WorkoutLogDto log = memberWorkoutLogService.getWorkoutLog(memberId, logId);

    return ResponseEntity.ok().body(log);
  }

  @PostMapping("/workoutlogs")
  public ResponseEntity<Long> createWorkoutLog(
      @RequestBody WorkoutLogCreateRequestDto request,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    LocalDate date = request.getDate();
    List<Workout> exerciseList = request.getExerciseList();

    Long memberId = userDetails.getId();

    Long logId = memberWorkoutLogService.createWorkoutLog(date, exerciseList, memberId);

    return ResponseEntity.ok(logId);
  }

  @GetMapping("/workoutlogs/{logId}/feedbacks")
  public ResponseEntity<List<WorkoutFeedbackResponseDto>> getWorkoutFeedbackList(
      @PathVariable Long logId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    List<WorkoutFeedbackResponseDto> list = memberWorkoutFeedbackService.getFeedbackList(
        memberId, logId);

    return ResponseEntity.ok(list);
  }

}
