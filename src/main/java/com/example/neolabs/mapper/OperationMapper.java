package com.example.neolabs.mapper;

import com.example.neolabs.dto.OperationDto;
import com.example.neolabs.entity.operation.*;
import com.example.neolabs.enums.EntityEnum;
import com.example.neolabs.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OperationMapper {

    public OperationDto applicationOperationToDto(ApplicationOperation operation){
        return OperationDto.builder()
                .target(operation.getApplication())
                .targetId(operation.getApplication().getId())
                .targetType(EntityEnum.APPLICATION)
                .description(operation.getDescription())
                .user(UserMapper.entityToDto(operation.getUser()))
                .type(operation.getOperationType())
                .rawDate(operation.getCreatedDate())
                .date(DateUtil.dateFormatter.format(operation.getCreatedDate()))
                .time(DateUtil.timeFormatter.format(operation.getCreatedDate()))
                .build();
    }

    public List<OperationDto> applicationOperationListToDtoList(List<ApplicationOperation> operations){
        return operations.stream().map(this::applicationOperationToDto).collect(Collectors.toList());
    }

    public OperationDto userOperationToDto(UserOperation operation){
        return OperationDto.builder()
                .target(operation.getTargetUser())
                .targetId(operation.getTargetUser().getId())
                .targetType(EntityEnum.APPLICATION)
                .description(operation.getDescription())
                .user(UserMapper.entityToDto(operation.getUser()))
                .type(operation.getOperationType())
                .rawDate(operation.getCreatedDate())
                .date(DateUtil.dateFormatter.format(operation.getCreatedDate()))
                .time(DateUtil.timeFormatter.format(operation.getCreatedDate()))
                .build();
    }

    public List<OperationDto> userOperationListToDtoList(List<UserOperation> operations){
        return operations.stream().map(this::userOperationToDto).collect(Collectors.toList());
    }

    public OperationDto studentOperationToDto(StudentOperation operation){
        return OperationDto.builder()
                .target(operation.getStudent())
                .targetId(operation.getStudent().getId())
                .targetType(EntityEnum.APPLICATION)
                .description(operation.getDescription())
                .user(UserMapper.entityToDto(operation.getUser()))
                .type(operation.getOperationType())
                .rawDate(operation.getCreatedDate())
                .date(DateUtil.dateFormatter.format(operation.getCreatedDate()))
                .time(DateUtil.timeFormatter.format(operation.getCreatedDate()))
                .build();
    }

    public List<OperationDto> studentOperationListToDtoList(List<StudentOperation> operations){
        return operations.stream().map(this::studentOperationToDto).collect(Collectors.toList());
    }

    public OperationDto groupOperationToDto(GroupOperation operation){
        return OperationDto.builder()
                .target(operation.getGroup())
                .targetId(operation.getGroup().getId())
                .targetType(EntityEnum.APPLICATION)
                .description(operation.getDescription())
                .user(UserMapper.entityToDto(operation.getUser()))
                .type(operation.getOperationType())
                .rawDate(operation.getCreatedDate())
                .date(DateUtil.dateFormatter.format(operation.getCreatedDate()))
                .time(DateUtil.timeFormatter.format(operation.getCreatedDate()))
                .build();
    }

    public List<OperationDto> groupOperationListToDtoList(List<GroupOperation> operations){
        return operations.stream().map(this::groupOperationToDto).collect(Collectors.toList());
    }

    public OperationDto courseOperationToDto(CourseOperation operation){
        return OperationDto.builder()
                .target(operation.getCourse())
                .targetId(operation.getCourse().getId())
                .targetType(EntityEnum.APPLICATION)
                .description(operation.getDescription())
                .user(UserMapper.entityToDto(operation.getUser()))
                .type(operation.getOperationType())
                .rawDate(operation.getCreatedDate())
                .date(DateUtil.dateFormatter.format(operation.getCreatedDate()))
                .time(DateUtil.timeFormatter.format(operation.getCreatedDate()))
                .build();
    }

    public List<OperationDto> courseOperationListToDtoList(List<CourseOperation> operations){
        return operations.stream().map(this::courseOperationToDto).collect(Collectors.toList());
    }

    public OperationDto mentorOperationToDto(MentorOperation operation){
        return OperationDto.builder()
                .target(operation.getMentor())
                .targetId(operation.getMentor().getId())
                .targetType(EntityEnum.APPLICATION)
                .description(operation.getDescription())
                .user(UserMapper.entityToDto(operation.getUser()))
                .type(operation.getOperationType())
                .rawDate(operation.getCreatedDate())
                .date(DateUtil.dateFormatter.format(operation.getCreatedDate()))
                .time(DateUtil.timeFormatter.format(operation.getCreatedDate()))
                .build();
    }

    public List<OperationDto> mentorOperationListToDtoList(List<MentorOperation> operations){
        return operations.stream().map(this::mentorOperationToDto).collect(Collectors.toList());
    }

//TODO:SAKU LOOK

//    public OperationDto departmentOperationToDto(DepartmentOperation operation){
//        return OperationDto.builder()
//                .target(operation.getDepartment())
//                .targetId(operation.getDepartment().getId())
//                .targetType(EntityEnum.APPLICATION)
//                .description(operation.getDescription())
//                .user(UserMapper.entityToDto(operation.getUser()))
//                .type(operation.getOperationType())
//                .rawDate(operation.getCreatedDate())
//                .date(DateUtil.dateFormatter.format(operation.getCreatedDate()))
//                .time(DateUtil.timeFormatter.format(operation.getCreatedDate()))
//                .build();
//    }
//
//    public List<OperationDto> departmentOperationListToDtoList(List<DepartmentOperation> operations){
//        return operations.stream().map(this::departmentOperationToDto).collect(Collectors.toList());
//    }

    public OperationDto paymentOperationToDto(PaymentOperation operation){
        return OperationDto.builder()
                .target(operation.getPayment())
                .targetId(operation.getPayment().getId())
                .targetType(EntityEnum.APPLICATION)
                .description(operation.getDescription())
                .user(UserMapper.entityToDto(operation.getUser()))
                .type(operation.getOperationType())
                .rawDate(operation.getCreatedDate())
                .date(DateUtil.dateFormatter.format(operation.getCreatedDate()))
                .time(DateUtil.timeFormatter.format(operation.getCreatedDate()))
                .build();
    }

    public List<OperationDto> paymentOperationListToDtoList(List<PaymentOperation> operations){
        return operations.stream().map(this::paymentOperationToDto).collect(Collectors.toList());
    }

    //TODO:SAKULOOK
//    public List<OperationDto> allOperationListToDtoList(List<ApplicationOperation> applicationOperations,
//                                                        List<UserOperation> userOperations,
//                                                        List<GroupOperation> groupOperations,
//                                                        List<StudentOperation> studentOperations,
//                                                        List<DepartmentOperation> departmentOperations,
//                                                        List<CourseOperation> courseOperations,
//                                                        List<MentorOperation> mentorOperations,
//                                                        List<PaymentOperation> paymentOperations){
//        List<OperationDto> operations = new ArrayList<>();
//        if (applicationOperations != null){
//            operations.addAll(applicationOperationListToDtoList(applicationOperations));
//        }
//        if (userOperations != null){
//            operations.addAll(userOperationListToDtoList(userOperations));
//        }
//        if (groupOperations != null){
//            operations.addAll(groupOperationListToDtoList(groupOperations));
//        }
//        if (studentOperations != null){
//            operations.addAll(studentOperationListToDtoList(studentOperations));
//        }
//        if (departmentOperations != null){
//            operations.addAll(departmentOperationListToDtoList(departmentOperations));
//        }
//        if (courseOperations != null){
//            operations.addAll(courseOperationListToDtoList(courseOperations));
//        }
//        if (mentorOperations != null){
//            operations.addAll(mentorOperationListToDtoList(mentorOperations));
//        }
//        if (paymentOperations != null){
//            operations.addAll(paymentOperationListToDtoList(paymentOperations));
//        }
//        Collections.sort(operations);
//        return operations;
//    }
}
