package com.example.bank.service;

import com.example.bank.dto.CustomerDtO;
import com.example.bank.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    //crud
    public List<CustomerDtO> getAllCustomers();
   // public CustomerDtO addCustomer(CustomerDtO customerDtO);
    public CustomerDtO getCustomerById(Long id);
    public CustomerDtO updateCustomer(Long id, CustomerDtO customerDtO);
    public void deleteCustomer(Long id);



    //pagination and sorting
    public List<CustomerDtO> sortedCustomers(String field);
    public Page<CustomerDtO> paginatedCustomer(int pageNo, int pageSize);
    public Page<CustomerDtO> paginationWithSorting(int pageNo, int pageSize,String field);






}
