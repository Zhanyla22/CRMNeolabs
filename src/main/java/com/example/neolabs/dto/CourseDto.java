package com.example.neolabs.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name can't be empty or null")
    private String name;

    @NotBlank(message = "Cost can't be empty or null")
    private Double cost;

    @NotBlank(message = "Duration can't be empty or null")
    private Integer duration_in_month;

    private Integer numberOfLessons;


    //    @NotBlank(message = "Level can't be empty or null")
//    private Level level;

//    @JsonProperty(value = "department", access = JsonProperty.Access.READ_ONLY)
//    private DepartmentDto department;

    //    @NotBlank(message = "Status can't be empty or null")
//    private Status status;
}
