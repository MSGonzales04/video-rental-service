package com.app.controller;

import com.app.dto.request.CustomerRequest;
import com.app.dto.response.CustomerResponse;
import com.app.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/v1")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<CustomerResponse> getCustomer(
            @RequestHeader(value = "public_user_id", required = true) String publicUserId) {
        CustomerResponse response = customerService.getCustomerByPublicUserId(publicUserId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
