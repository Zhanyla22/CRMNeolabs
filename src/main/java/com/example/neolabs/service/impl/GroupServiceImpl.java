package com.example.neolabs.service.impl;

import com.example.neolabs.dto.GroupDto;
import com.example.neolabs.dto.ResponseDto;
import com.example.neolabs.dto.request.ArchiveRequest;
import com.example.neolabs.dto.request.create.CreateGroupRequest;
import com.example.neolabs.dto.request.update.UpdateGroupRequest;
import com.example.neolabs.entity.Course;
import com.example.neolabs.entity.Group;
import com.example.neolabs.enums.EntityEnum;
import com.example.neolabs.enums.OperationType;
import com.example.neolabs.enums.Status;
import com.example.neolabs.exception.BaseException;
import com.example.neolabs.exception.EntityNotFoundException;
import com.example.neolabs.mapper.GroupMapper;
import com.example.neolabs.repository.GroupRepository;
import com.example.neolabs.service.GroupService;
import com.example.neolabs.util.DateUtil;
import com.example.neolabs.util.ResponseUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupServiceImpl implements GroupService {

    final GroupRepository groupRepository;
    final CourseServiceImpl courseService;
    final OperationServiceImpl operationService;
    final MentorServiceImpl mentorService;
    final GroupMapper groupMapper;

    @Override
    public GroupDto insertGroup(CreateGroupRequest createGroupRequest) {
        Course course = courseService.getCourseEntityById(createGroupRequest.getCourseId());

        Group group = GroupMapper.createGroupRequestToEntity(createGroupRequest);
        group.setCourse(course);
        group.setIsArchived(false);
        group.setMentor(mentorService.getMentorEntityById(createGroupRequest.getMentorId()));
        group.setEndDate(group.getStartDate().plusMonths(course.getDurationInMonth()));
        group = groupRepository.save(group);
        operationService.recordGroupOperation(groupRepository.save(group), OperationType.CREATE);
        return GroupMapper.entityToDto(group);
    }

    @Override
    public List<GroupDto> getAllGroups() {
        return GroupMapper.entityListToDtoList(groupRepository.findAll());
    }

    @Override
    public GroupDto getGroupById(Long groupId) {
        return GroupMapper.entityToDto(getGroupEntityById(groupId));
    }

    @Override
    public GroupDto updateGroupById(Long groupId, UpdateGroupRequest updateGroupRequest) {
        Group group = groupMapper.updateEntityWithUpdateRequest(getGroupEntityById(groupId), updateGroupRequest);
        return GroupMapper.entityToDto(groupRepository.save(group));
    }

    @Override
    public ResponseDto archiveGroupById(Long groupId, ArchiveRequest archiveRequest) {
        Group group = getGroupEntityById(groupId);
        if (group.getStatus() == Status.ARCHIVED) {
            throw new BaseException("Group is already archived", HttpStatus.CONFLICT);
        }
        group.setReason(archiveRequest.getReason());
        group.setStatus(Status.ARCHIVED);
        group.setArchiveDate(LocalDateTime.now(DateUtil.getZoneId()));
        group.setIsArchived(true);
        operationService.recordGroupOperation(groupRepository.save(group), OperationType.ARCHIVE);
        return ResponseUtil.buildSuccessResponse("Group has been successfully archived.");
    }

    @Override
    public ResponseDto unarchiveGroupById(Long groupId) {
        Group group = getGroupEntityById(groupId);
        if (group.getStatus() == Status.ACTIVE) {
            throw new BaseException("Group is not archived.", HttpStatus.CONFLICT);
        }
        group.setStatus(Status.ACTIVE);
        group.setReason(null);
        group.setArchiveDate(null);
        group.setIsArchived(false);
        operationService.recordGroupOperation(groupRepository.save(group), OperationType.UNARCHIVE);
        return ResponseUtil.buildSuccessResponse("Group has been successfully unarchived.");
    }


    public Group getGroupEntityById(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> {
            throw new EntityNotFoundException(EntityEnum.GROUP, "id", groupId);
        });
    }
}
