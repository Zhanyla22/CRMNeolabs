package com.example.neolabs.dto;

import com.example.neolabs.entity.Department;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MentorCardDto {

    String imageUrl;

    String firstName;

    String lastName;

    String email;

    String department;

    SimpleDateFormat dateArchive;

    String reasonArchive;
}