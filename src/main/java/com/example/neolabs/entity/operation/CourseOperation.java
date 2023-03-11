package com.example.neolabs.entity.operation;

import com.example.neolabs.entity.Course;
import com.example.neolabs.entity.User;
import com.example.neolabs.entity.base.BaseEntity;
import com.example.neolabs.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_operations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseOperation extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(columnDefinition = "course_id",
            referencedColumnName = "id")
    Course course;

    @ManyToOne(optional = false)
    @JoinColumn(columnDefinition = "user_id",
            referencedColumnName = "id")
    User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    OperationType operationType;

    String description;
}
