package com.example.bank.mapper;

import com.example.bank.dto.CustomerDtO;
import com.example.bank.entity.Customer;

public class CustomerMapper {

    // Convert DTO to Entity (Use a default password if needed)

    public static Customer mapToEntity(CustomerDtO customerDtO) {
        return new Customer(
                customerDtO.getId(),
                customerDtO.getUsername(),
                customerDtO.getEmail(),
                customerDtO.getAmount(),
                null
        );
    }

    // Convert Entity to DTO (Excluding password)
    public static CustomerDtO mapToDtO(Customer customer) {
        return new CustomerDtO(
                customer.getId(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getAmount()
        );
    }

}
