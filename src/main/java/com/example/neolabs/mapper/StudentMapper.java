package com.example.neolabs.mapper;

import com.example.neolabs.dto.StudentCardDto;
import com.example.neolabs.dto.StudentDto;
import com.example.neolabs.dto.request.create.CreateStudentRequest;
import com.example.neolabs.dto.request.update.UpdateStudentRequest;
import com.example.neolabs.entity.Student;
import com.example.neolabs.enums.Status;
import com.example.neolabs.util.DateUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    public static Student createRequestToEntity(CreateStudentRequest request) {
        return Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static Student updateRequestToEntity(UpdateStudentRequest request) {
        return Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static StudentDto entityToDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender().getRussian())
                .status(student.getStatus().getRussian())
                .isArchived(student.getIsArchived())
                .archiveReason(student.getReason())
                .archiveDate(student.getArchiveDate() != null ?
                        student.getArchiveDate().format(DateUtil.datetimeToDateFormatter) : null)
                .totalDebt(null) // FIXME: 16.03.2023
                .totalPaymentPercentage(null)
                .build();
    }

    public static StudentCardDto entityToCardDto(Student student){
        return StudentCardDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus().getRussian())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .hasLaptop((student.getApplication() != null ? student.getApplication().getHasLaptop() : true) ? "есть" : "нет")// FIXME: 17.04.2023
                .build();
    }

    public static Student updateEntityWithUpdateRequest(Student student, UpdateStudentRequest request){
        student.setEmail(request.getEmail() != null ? request.getEmail() : student.getEmail());
        student.setFirstName(request.getFirstName() != null ? request.getFirstName() : student.getFirstName());
        student.setLastName(request.getLastName() != null ? request.getLastName() : student.getLastName());
        student.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : student.getPhoneNumber());
        return student;
    }

    public static List<StudentCardDto> entityListToCardDtoList(List<Student> students){
        return students.stream().map(StudentMapper::entityToCardDto).collect(Collectors.toList());
    }

    public static List<StudentDto> entityListToDtoList(List<Student> students) {
        return students.stream().map(StudentMapper::entityToDto).collect(Collectors.toList());
    }
}
