package com.app.controller;

import com.app.dto.request.CustomerRequest;
import com.app.dto.response.ApiResponse;
import com.app.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<ApiResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        ApiResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<ApiResponse> getCustomer(
            @RequestHeader(value = "public_user_id", required = true) String publicUserId) {
        ApiResponse response = customerService.getCustomerByPublicUserId(publicUserId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PostMapping("/updateCustomer")
//    public ResponseEntity<ApiResponse> updateCustomer(
//            @RequestHeader(value = "public_user_id", required = true) String publicUserId,
//            @Valid @RequestBody CustomerRequest request) {
//        ApiResponse response = customerService.updateCustomer(publicUserId, request);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
}
