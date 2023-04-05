package com.example.neolabs.dto;

import com.example.neolabs.enums.Gender;
import com.example.neolabs.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDto {
    @JsonProperty("email")
    String email;

    @JsonProperty("firstName")
    String firstName;

    @JsonProperty("lastName")
    String lastName;

    @JsonProperty("phoneNumber")
    String phoneNumber;

    @JsonProperty("gender")
    Gender gender;

    @JsonProperty("status")
    Status status;

    @JsonProperty("totalDebt")
    Integer totalDebt;

    @JsonProperty("paymentPercentage")
    Integer totalPaymentPercentage;

    @JsonProperty("groups")
    List<GroupDto> groups;
}
