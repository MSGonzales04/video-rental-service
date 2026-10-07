package com.app.dto.response;

import com.app.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {

    @JsonIgnore
    private String publicUserId;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private LocalDate birthDate;
    private LocalDateTime createdDate;

    public CustomerResponse(Customer customer) {
        if (customer != null) {
            this.publicUserId = customer.getPublicUserId();
            this.firstName = customer.getFirstName();
            this.lastName = customer.getLastName();
            this.middleInitial = customer.getMiddleInitial();
            this.birthDate = customer.getBirthDate();
            this.createdDate = customer.getCreatedDate();
        }
    }
}
