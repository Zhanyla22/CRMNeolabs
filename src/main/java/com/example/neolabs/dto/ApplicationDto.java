package com.example.neolabs.dto;

import com.example.neolabs.enums.ApplicationStatus;
import com.example.neolabs.enums.Education;
import com.example.neolabs.enums.Gender;
import com.example.neolabs.enums.MarketingStrategyEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    Long id;

    @NotEmpty
    @NotNull
    @JsonProperty(value = "firstName")
    String firstName;

    @NotEmpty
    @NotNull
    @JsonProperty(value = "lastName")
    String lastName;

    @NotEmpty
    @NotNull
    @Email
    @JsonProperty(value = "email")
    String email;

    @NotEmpty
    @NotNull
    @JsonProperty(value = "phoneNumber")
    String phoneNumber;

    @NotEmpty
    @NotNull
    @JsonProperty(value = "gender")
    Gender gender;

    @NotEmpty
    @NotNull
    @JsonProperty(value = "hasLaptop")
    Boolean hasLaptop;

    @NotEmpty
    @NotNull
    @JsonProperty(value = "marketingStrategy")
    MarketingStrategyEnum marketingStrategyEnum;

    //TODO: SAKU LOOK
//    @JsonProperty(value = "department", access = JsonProperty.Access.READ_ONLY)
//    DepartmentDto departmentDTO;

    @NotEmpty
    @NotNull
    @JsonProperty(value = "departmentId", access = JsonProperty.Access.WRITE_ONLY)
    Long departmentId;

    @JsonProperty(value = "isArchived", access = JsonProperty.Access.READ_ONLY)
    Boolean isArchived;

    @JsonProperty(value = "reason")
    String reason;

    @JsonProperty(value = "applicationStatusName", access = JsonProperty.Access.READ_ONLY)
    ApplicationStatus applicationStatus;

    @JsonProperty(value = "applicationStatusNum", access = JsonProperty.Access.READ_ONLY)
    Integer applicationStatusNum;

    @JsonProperty(value = "applicationInitialStatusNum", access = JsonProperty.Access.WRITE_ONLY)
    Integer applicationStatusInitialNum;

    @JsonProperty(value = "applicationStatusUpdateDate", access = JsonProperty.Access.READ_ONLY)
    String applicationStatusUpdateDate;

    @JsonProperty(value = "applicationStatusUpdateTime", access = JsonProperty.Access.READ_ONLY)
    String applicationStatusUpdateTime;

    @JsonProperty(value = "creationDate", access = JsonProperty.Access.READ_ONLY)
    String creationDate;

    @JsonProperty(value = "updateDate", access = JsonProperty.Access.READ_ONLY)
    String updateDate;

    @NotEmpty
    @NotNull
    @JsonProperty(value = "education")
    Education education;

    @JsonProperty(value = "isUrgent", access = JsonProperty.Access.READ_ONLY)
    Boolean isUrgent;
}
