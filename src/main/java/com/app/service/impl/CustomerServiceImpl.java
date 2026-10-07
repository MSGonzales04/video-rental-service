package com.app.service.impl;

import com.app.dto.request.CustomerRequest;
import com.app.dto.response.ApiResponse;
import com.app.dto.response.CustomerResponse;
import com.app.exception.ResponseException;
import com.app.model.Customer;
import com.app.repository.CustomerRepository;
import com.app.service.CustomerService;
import com.app.util.EntityHelper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final EntityHelper entityHelper;

    @Override
    @Transactional
    public ApiResponse createCustomer(CustomerRequest customerRequest) {
            boolean exists = customerRepository.existsByFirstNameAndLastNameAndBirthDate(
                    customerRequest.getFirstName(),
                    customerRequest.getLastName(),
                    customerRequest.getBirthDate());

            // Validate if customer is already registered.
            if (exists) {
                throw new ResponseException(
                        HttpStatus.CONFLICT,
                        "Customer already existing"
                );
            }

            try {
                ApiResponse apiResponse = new ApiResponse();
                Customer customer = Customer.builder()
                        .firstName(customerRequest.getFirstName())
                        .lastName(customerRequest.getLastName())
                        .middleInitial(customerRequest.getMiddleInitial())
                        .birthDate(customerRequest.getBirthDate())
                        .build();

                Customer savedCustomer = customerRepository.save(customer);
                entityHelper.refresh(savedCustomer);

                Optional.ofNullable(savedCustomer.getPublicUserId())
                        .ifPresent(puid -> log.info("Customer successfully added [PUID: {}]", puid));

                return ApiResponse.builder()
                        .message("Customer successfully created")
                        .customerResponse(new CustomerResponse(savedCustomer))
                        .build();

            } catch (Exception e) {
                log.error("Failed to register customer", e);
                throw new ResponseException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred while creating the customer",
                        e
                );
            }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse getCustomerByPublicUserId(String publicUserId) {
        Customer customer = customerRepository.findByPublicUserId(publicUserId)
                .orElseThrow(() -> new ResponseException(
                        HttpStatus.NOT_FOUND,
                        "Customer does not exist"
                ));
        return ApiResponse.builder()
                .message("Customer successfully retrieved")
                .customerResponse(new CustomerResponse(customer))
                .build();
    }

//    @Override
//    @Transactional
//    public ApiResponse updateCustomer(String publicUserId, CustomerRequest customerRequest) {
//        Customer customer = customerRepository.findByPublicUserId(publicUserId)
//                .orElseThrow(() -> new ResponseException(
//                        HttpStatus.BAD_REQUEST,
//                        "Customer does not exist"
//                ));
//
//        boolean duplicateExists = customerRepository.existsByFirstNameAndLastNameAndBirthDate(
//                customerRequest.getFirstName(),
//                customerRequest.getLastName(),
//                customerRequest.getBirthDate()
//        );
//
//        boolean isSameCustomer = customer.getFirstName().equalsIgnoreCase(customerRequest.getFirstName())
//                && customer.getLastName().equalsIgnoreCase(customerRequest.getLastName())
//                && customer.getBirthDate().equals(customerRequest.getBirthDate());
//
//        if (duplicateExists && !isSameCustomer) {
//            throw new ResponseException(
//                    HttpStatus.CONFLICT,
//                    "Another customer already exists with the given details"
//            );
//        }
//
//        try {
//            customer.setFirstName(customerRequest.getFirstName());
//            customer.setLastName(customerRequest.getLastName());
//            customer.setMiddleInitial(customerRequest.getMiddleInitial());
//            customer.setBirthDate(customerRequest.getBirthDate());
//
//            Customer updatedCustomer = customerRepository.save(customer);
//
//            log.info("Customer successfully updated [PUID: {}]", publicUserId);
//
//            return ApiResponse.builder()
//                    .message("Customer successfully updated")
//                    .customerResponse(new CustomerResponse(updatedCustomer))
//                    .build();
//
//        } catch (ResponseException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("Failed to update customer [PUID: {}]", publicUserId, e);
//            throw new ResponseException(
//                    HttpStatus.INTERNAL_SERVER_ERROR,
//                    "An unexpected error occurred while updating the customer",
//                    e
//            );
//        }
//    }
}
