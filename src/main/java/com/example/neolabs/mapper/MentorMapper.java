package com.example.neolabs.mapper;

import com.example.neolabs.dto.request.create.CreateMentorRequest;
import com.example.neolabs.dto.MentorCardDto;
import com.example.neolabs.entity.Mentor;
import com.example.neolabs.enums.Status;
import com.example.neolabs.exception.BaseException;
import com.example.neolabs.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MentorMapper {

    final CourseRepository courseRepository;

    public static MentorCardDto mentorEntityToMentorCardDto(Mentor mentor) {
        MentorCardDto mentorCardDto = new MentorCardDto();
        mentorCardDto.setEmail(mentor.getEmail());
        mentorCardDto.setFirstName(mentor.getFirstName());
        mentorCardDto.setLastName(mentor.getLastName());
        mentorCardDto.setImageUrl(mentor.getImageUrl());
        mentorCardDto.setCourse(mentor.getCourse().getName());
        mentorCardDto.setDateArchive(mentor.getUpdatedDate());
        mentorCardDto.setReasonArchive(mentor.getReason());
        return mentorCardDto;
    }

    public Mentor createMentorDtoToMentorEntity(CreateMentorRequest createMentorRequest) {
        Mentor mentor = new Mentor();
        mentor.setEmail(createMentorRequest.getEmail());
        mentor.setFirstName(createMentorRequest.getFirstName());
        mentor.setLastName(createMentorRequest.getLastName());
        mentor.setPhoneNumber(createMentorRequest.getPhoneNumber());
        mentor.setCourse(courseRepository.findCourseByName(createMentorRequest.getCourseName())
                .orElseThrow(
                        () -> new BaseException("course " + createMentorRequest.getCourseName() + " not found", HttpStatus.BAD_REQUEST)));
        mentor.setStatus(Status.ACTIVE);
        return mentor;
    }

}
