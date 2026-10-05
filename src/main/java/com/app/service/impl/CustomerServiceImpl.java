package com.app.service.impl;

import com.app.dto.request.CustomerRequest;
import com.app.dto.response.CustomerResponse;
import com.app.model.Customer;
import com.app.repository.CustomerRepository;
import com.app.service.CustomerService;
import com.app.util.EntityHelper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final EntityHelper entityHelper;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        boolean exists = customerRepository.existsByFirstNameAndLastNameAndBirthDate(customerRequest.getFirstName(), customerRequest.getLastName(), customerRequest.getBirthDate());

        if(exists){
            log.warn("Customer creation rejected: Duplicate record found for {} {} ({})",
                    customerRequest.getFirstName(),
                    customerRequest.getLastName(),
                    customerRequest.getBirthDate());
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User already exists"
            );
        }

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

        return new CustomerResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByPublicUserId(String publicUserId) {
        Customer customer = customerRepository.findByPublicUserId(publicUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "User not found"
                ));

        return new CustomerResponse(customer);
    }
}
