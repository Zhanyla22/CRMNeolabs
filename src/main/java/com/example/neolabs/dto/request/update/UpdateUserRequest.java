package com.example.neolabs.dto.request.update;

import com.example.neolabs.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {

    String email;

    Role role;

    String phoneNumber;

    String firstName;

    String lastName;
}
