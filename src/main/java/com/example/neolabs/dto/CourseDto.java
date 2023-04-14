package com.example.neolabs.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    Long id;

    @NotBlank(message = "Name can't be empty or null")
    @JsonProperty(value = "name")
    String name;

    @NotBlank(message = "Cost can't be empty or null")
    @JsonProperty(value = "cost")
    Double cost;

    @NotBlank(message = "Duration can't be empty or null")
    @JsonProperty(value = "durationInMonth")
    Integer durationInMonth;

    @JsonProperty(value = "numberOfLessons")
    Integer numberOfLessons;

    @JsonProperty(value = "imageUrl")
    String imageUrl;
}
