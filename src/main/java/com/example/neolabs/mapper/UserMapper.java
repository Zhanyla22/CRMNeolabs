package com.example.neolabs.mapper;

import com.example.neolabs.dto.UserDto;
import com.example.neolabs.entity.User;
import com.example.neolabs.util.DateUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserDto entityToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .isArchived(user.getIsArchived())
                .archiveReason(user.getReason())
                .archiveDate(user.getArchiveDate() != null ?
                        user.getArchiveDate().format(DateUtil.datetimeToDateFormatter) : null)
                .lastVisitDate(DateUtil.datetimeToDateFormatter.format(user.getLastVisitDate()))
                .lastVisitTime(DateUtil.datetimeFormatter.format(user.getLastVisitDate()))
                .build();
    }

    public static List<UserDto> entityListToDtoList(List<User> users){
        return users.stream().map(UserMapper::entityToDto).collect(Collectors.toList());
    }
}
