package com.example.neolabs.dto.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OutgoingMessage {
    @JsonProperty("application_id")
    Long applicationId;

    @JsonProperty("old_status")
    Integer oldStatus;

    @JsonProperty("new_status")
    Integer newStatus;

    @JsonProperty("user_id")
    Long userId;
}
