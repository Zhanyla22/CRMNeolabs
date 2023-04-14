package com.example.neolabs.service.impl;

import com.example.neolabs.dto.ArchiveDto;
import com.example.neolabs.dto.MentorCardDto;
import com.example.neolabs.dto.MentorResponse;
import com.example.neolabs.dto.UpdateMentorDto;
import com.example.neolabs.dto.request.create.CreateMentorRequest;
import com.example.neolabs.entity.Mentor;
import com.example.neolabs.enums.EntityEnum;
import com.example.neolabs.enums.Status;
import com.example.neolabs.exception.BaseException;
import com.example.neolabs.exception.EntityNotFoundException;
import com.example.neolabs.mapper.GroupMapper;
import com.example.neolabs.mapper.MentorMapper;
import com.example.neolabs.repository.CourseRepository;
import com.example.neolabs.repository.GroupRepository;
import com.example.neolabs.repository.MentorRepository;
import com.example.neolabs.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    final MentorRepository mentorRepository;
    final MentorMapper mentorMapper;
    // TODO: 14.04.2023 if possible its better to NOT use other repos in service class
    final CourseRepository courseRepository;
    final GroupRepository groupRepository;
    final CourseServiceImpl courseService;
    final GroupMapper groupMapper;

    @Override
    public List<MentorCardDto> getAllMentorCards(Long courseId, Status status) {
        ExampleMatcher exampleMatcher = getExampleMatcherForCards();
        Mentor probe = Mentor.builder()
                .status(status)
                .course(courseId != null ? courseService.getCourseEntityById(courseId) : null)
                .build();
        return mentorMapper.entityListToCardList(mentorRepository.findAll(Example.of(probe, exampleMatcher)));
    }

    @Override
    public void addNewMentor(CreateMentorRequest createMentorRequest) {
        if (!mentorRepository.existsByEmail(createMentorRequest.getEmail())) {
            mentorRepository.save(mentorMapper.createMentorDtoToMentorEntity(createMentorRequest));
        } else
            throw new BaseException("mentor with email " + createMentorRequest.getEmail() + " already exists", HttpStatus.BAD_REQUEST);
    }

    @Override
    public void deleteMentorById(Long id) {
        Mentor mentor = getMentorEntityById(id);
        mentor.setStatus(Status.DELETED);
        mentor.setDeletedDate(LocalDateTime.now());
        mentorRepository.save(mentor);
    }

    @Override
    public void updateMentorById(UpdateMentorDto updateMentorDto, Long id) {
        Mentor mentor = getMentorEntityById(id);
        mentor.setEmail(updateMentorDto.getEmail());
        mentor.setFirstName(updateMentorDto.getFirstName());
        mentor.setLastName(updateMentorDto.getLastName());
        mentor.setPhoneNumber(updateMentorDto.getPhoneNumber());
        mentor.setPatentNumber(updateMentorDto.getPatentNumber());
        mentor.setSalary(updateMentorDto.getSalary());
        mentor.setCourse(courseRepository.findById(updateMentorDto.getCourseId())
                .orElseThrow(
                        () -> new BaseException("course with id  " + updateMentorDto.getCourseId() + " not found", HttpStatus.BAD_REQUEST)));
        mentorRepository.save(mentor);
    }

    @Override
    public void archiveMentorById(Long mentorId, ArchiveDto mentorArchiveDto) {
        Mentor mentor = getMentorEntityById(mentorId);
        mentor.setUpdatedDate(LocalDateTime.now());
        mentor.setReason(mentorArchiveDto.getReason());
        mentor.setStatus(Status.ARCHIVED);

        mentorRepository.save(mentor);
    }

    @Override
    public void blackListMentorById(Long mentorId, ArchiveDto mentorBlacklistDto) {
        Mentor mentor = getMentorEntityById(mentorId);
        mentor.setUpdatedDate(LocalDateTime.now());
        mentor.setReason(mentorBlacklistDto.getReason());
        mentor.setStatus(Status.BLACK_LIST);

        mentorRepository.save(mentor);
    }

    @Override
    public MentorResponse getMentorById(Long id) {
        Mentor mentor = getMentorEntityById(id);
        MentorResponse mentorResponse = new MentorResponse();
        mentorResponse.setFirstName(mentor.getFirstName());
        mentorResponse.setLastName(mentor.getLastName());
        mentorResponse.setPhoneNumber(mentor.getPhoneNumber());
        mentorResponse.setPatentNumber(mentor.getPatentNumber());
        mentorResponse.setEmail(mentor.getEmail());
        mentorResponse.setCourseName(mentor.getCourse().getName());
        mentorResponse.setGroupName(groupRepository.findGroupsNameByMentorId(id));

        return mentorResponse;

    }

    public Mentor getMentorEntityById(Long mentorId) {
        return mentorRepository.findById(mentorId)
                .orElseThrow(
                        () -> new EntityNotFoundException(EntityEnum.MENTOR, "id", mentorId)
                );
    }

    private ExampleMatcher getExampleMatcherForCards() {
        return ExampleMatcher.matchingAll()
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("course", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("id");
    }
}
