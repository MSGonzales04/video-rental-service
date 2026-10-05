package com.app.service;

import com.app.dto.request.CustomerRequest;
import com.app.dto.response.CustomerResponse;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse getCustomerByPublicUserId(String publicUserId);
}
