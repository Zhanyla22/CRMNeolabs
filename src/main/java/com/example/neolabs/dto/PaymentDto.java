package com.example.neolabs.dto;


import com.example.neolabs.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Long studentId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Double amount;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    TransactionType transactionType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Double totalDebt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Double totalPayment;

}
