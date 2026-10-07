package com.app.service;

import com.app.dto.request.CustomerRequest;
import com.app.dto.response.ApiResponse;
import com.app.dto.response.CustomerResponse;

public interface CustomerService {
    ApiResponse createCustomer(CustomerRequest customerRequest);
    ApiResponse getCustomerByPublicUserId(String publicUserId);
//    ApiResponse updateCustomer(String publicUserId, CustomerRequest customerRequest);
}
