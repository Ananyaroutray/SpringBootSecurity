package com.example.bank.service;

import com.example.bank.dto.CustomerDtO;
import com.example.bank.entity.Customer;
import com.example.bank.exception.ResourceNotFound;
import com.example.bank.mapper.CustomerMapper;
import com.example.bank.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;




    @Override
    public List<CustomerDtO> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            System.out.println("No customers found in the database.");
        } else {
            System.out.println("Fetched customers: " + customers);
        }
        return customers.stream().map(CustomerMapper::mapToDtO).collect(Collectors.toList());
    }


    @Override
    public CustomerDtO addCustomer(CustomerDtO customerDtO) {
        Customer customer = CustomerMapper.mapToEntity(customerDtO);
        Customer savedCustomer = customerRepo.save(customer);
        return CustomerMapper.mapToDtO(savedCustomer);
    }

    @Override
    public CustomerDtO getCustomerById(Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new ResourceNotFound("id not found"));
        return CustomerMapper.mapToDtO(customer);
    }


    @Override
    public CustomerDtO updateCustomer(Long id, CustomerDtO customerDtO) {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new ResourceNotFound("id not found to update"));
        customer.setEmail(customerDtO.getEmail());
        customer.setAmount(customerDtO.getAmount());
        Customer savedCustomer = customerRepo.save(customer);
        return CustomerMapper.mapToDtO(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Id not found to delete"));
        customerRepo.deleteById(id);
    }

    @Override
    public List<CustomerDtO> sortedCustomers( String field) {
        List<Customer> customers = customerRepo.findAll(Sort.by(field));
        return customers.stream().map(CustomerMapper::mapToDtO).collect(Collectors.toList());
    }


    @Override
    public Page<CustomerDtO> paginatedCustomer(int pageNo, int pageSize) {
        Pageable pages = PageRequest.of(pageNo,pageSize);
        Page<Customer> customers = customerRepo.findAll(pages);
        return customers.map(CustomerMapper::mapToDtO);
    }



    @Override
    public Page<CustomerDtO> paginationWithSorting(int pageNo, int pageSize, String field) {

        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(Sort.Direction.DESC,field));
        Page<Customer> customers = customerRepo.findAll(pageable);

        return customers.map(CustomerMapper::mapToDtO);
    }




}

