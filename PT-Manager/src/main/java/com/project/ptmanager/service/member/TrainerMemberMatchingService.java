package com.project.ptmanager.service.member;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.member.TrainerMemberMatching;
import com.project.ptmanager.dto.member.MatchingResponseDto;
import com.project.ptmanager.dto.member.TrainerMemberMatchingDto;
import com.project.ptmanager.exception.impl.MatchingNotFoundException;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.member.TrainerMemberMatchingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerMemberMatchingService {

  private final TrainerMemberMatchingRepository trainerMemberMatchingRepository;
  private final MemberRepository memberRepository;

  public MatchingResponseDto getByMemberId(Long memberId) {

    TrainerMemberMatching matching = trainerMemberMatchingRepository.findByMemberId(memberId)
        .orElseThrow(MatchingNotFoundException::new);

    Member trainer = matching.getTrainer();

    return MatchingResponseDto.builder()
        .id(trainer.getId())
        .name(trainer.getName())
        .build();
  }

  public List<MatchingResponseDto> getByTrainerId(Long trainerId) {

    List<TrainerMemberMatching> matchingList = trainerMemberMatchingRepository.findByTrainerId(
        trainerId);

    if (matchingList.isEmpty()) {
      throw new MatchingNotFoundException();
    }

    return matchingList.stream().map(e -> {
      Member member = e.getMember();
      return MatchingResponseDto.builder().id(member.getId()).name(member.getName()).build();
    }).toList();
  }

  public Long createMatching(TrainerMemberMatchingDto trainerMemberMatchingDto) {

    Long memberId = trainerMemberMatchingDto.getMemberId();
    Long trainerId = trainerMemberMatchingDto.getTrainerId();

    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    Member trainer = memberRepository.findById(trainerId).orElseThrow(MemberNotFoundException::new);

    TrainerMemberMatching matching = TrainerMemberMatching.builder()
        .member(member)
        .trainer(trainer)
        .build();

    TrainerMemberMatching saved = trainerMemberMatchingRepository.save(matching);

    return saved.getId();
  }
}
