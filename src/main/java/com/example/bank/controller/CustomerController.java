package com.example.bank.controller;

import com.example.bank.dto.CustomerDtO;
import com.example.bank.entity.Customer;
import com.example.bank.service.CustomerService;
import com.example.bank.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/api")
public class CustomerController {

    private final CustomerService customerService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("/addCustomer")
    public UserDetails addUser(@RequestBody Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return securityService.saveUser(customer);
    }


    @GetMapping
    public String greet(){
        return "Hello Buddy!!";
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDtO>> getCustomers(){
        List<CustomerDtO> customers=customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerDtO> getCustomerById(@PathVariable Long id){
        CustomerDtO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDtO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDtO customerDtO){
        CustomerDtO customer = customerService.updateCustomer(id,customerDtO);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("customer deleted successfully");
    }

    @GetMapping("/sorting")
    public ResponseEntity<List<CustomerDtO>> sortedCustomers(@RequestParam(name = "sortBy", required = false, defaultValue = "id") String field){
        List<CustomerDtO> customers = customerService.sortedCustomers(field);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<CustomerDtO>> paginatedCustomers(@RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,@RequestParam(name = "pageSize", required = false, defaultValue = "3" ) int pageSize){
        Page<CustomerDtO> customerDtOPage = customerService.paginatedCustomer(pageNo,pageSize);
        return ResponseEntity.ok(customerDtOPage);
    }

    @GetMapping("/sortingWithPagination")
    public ResponseEntity<Page<CustomerDtO>> paginatedAndSorted(@RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,@RequestParam(name = "pageSize", required = false, defaultValue = "3" ) int pageSize, @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String field)
    {
        Page<CustomerDtO> customerDtOS = customerService.paginationWithSorting(pageNo, pageSize, field);
        return ResponseEntity.ok(customerDtOS);
    }


}
