package com.example.neolabs.service;

import com.example.neolabs.dto.*;
import com.example.neolabs.dto.request.ArchiveRequest;
import com.example.neolabs.dto.request.create.CreateMentorRequest;
import com.example.neolabs.enums.Status;

import java.util.List;

public interface MentorService {

    List<MentorCardDto> getAllMentorCards(Long courseId, Status status);

    void addNewMentor(CreateMentorRequest createMentorRequest); // FIXME: 16.04.2023 need to return some info about operation

    void deleteMentorById(Long id); // FIXME: 16.04.2023 need to return some info about operation

    void updateMentorById(UpdateMentorDto updateMentorDto, Long id); // FIXME: 16.04.2023 need to return some info about operation

    ResponseDto archiveMentorById(Long mentorId, ArchiveRequest archiveRequest, Boolean isBlacklist);

    ResponseDto unarchiveMentorById(Long mentorId);

    MentorResponse getMentorById(Long id);

}
