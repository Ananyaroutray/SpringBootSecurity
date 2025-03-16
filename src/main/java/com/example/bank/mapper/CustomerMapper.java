package com.example.bank.mapper;

import com.example.bank.dto.CustomerDtO;
import com.example.bank.entity.Customer;

public class CustomerMapper {

    // Convert DTO to Entity (Use a default password if needed)

    public static Customer mapToEntity(CustomerDtO customerDtO) {
        return new Customer(
                customerDtO.getId(),
                customerDtO.getName(),
                customerDtO.getEmail(),
                customerDtO.getAmount(),
                null // Password is not needed here
        );
    }

    // Convert Entity to DTO (Excluding password)
    public static CustomerDtO mapToDtO(Customer customer) {
        return new CustomerDtO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAmount()
        );
    }

}
