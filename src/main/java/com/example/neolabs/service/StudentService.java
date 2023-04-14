package com.example.neolabs.service;

import com.example.neolabs.dto.ArchiveDto;
import com.example.neolabs.dto.ResponseDto;
import com.example.neolabs.dto.StudentDto;
import com.example.neolabs.dto.request.ConversionRequest;
import com.example.neolabs.dto.request.create.CreateStudentRequest;
import com.example.neolabs.dto.request.update.UpdateStudentRequest;
import com.example.neolabs.entity.Application;
import com.example.neolabs.enums.Status;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface StudentService {

    ResponseDto insertStudent(CreateStudentRequest createStudentRequest);

    void insertStudentFromApplication(Application application, ConversionRequest conversionRequest);

    List<StudentDto> getAllStudents(Status status, PageRequest pageRequest);

    List<StudentDto> filter(Long groupId, Status status);

    List<StudentDto> search(String email, String firstName, String lastName, String firstOrLastName, String phoneNumber);

    StudentDto getStudentById(Long studentId);

    ResponseDto updateStudentById(Long studentId, UpdateStudentRequest updateStudentRequest);

    ResponseDto enrollStudent(Long studentId, Long groupId);

    void archiveStudentById(Long studentId, ArchiveDto archiveStudentDto);

    void blacklistStudentById(Long studentId, ArchiveDto blacklistStudentDto);
}
