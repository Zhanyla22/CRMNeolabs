package com.example.neolabs.mapper;

import com.example.neolabs.dto.DepartmentDto;
import com.example.neolabs.entity.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentMapper {

    public DepartmentDto entityToDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .status(department.getStatus())
                .build();
    }

    public Department dtoToEntity(DepartmentDto departmentDTO) {
        return Department.builder()
                .name(departmentDTO.getName())
                .status(departmentDTO.getStatus())
                .build();
    }

    public List<DepartmentDto> entityListToDtoList(List<Department> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}