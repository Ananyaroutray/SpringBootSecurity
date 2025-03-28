package com.example.bank.controller;

import com.example.bank.JwtSecurity.JwtUtil;
import com.example.bank.dto.CustomerDtO;
import com.example.bank.entity.Customer;
import com.example.bank.service.CustomerService;
import com.example.bank.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/api")
public class CustomerController {

    private final CustomerService customerService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostMapping("/addCustomer")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        securityService.saveUser(customer);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully.");
        return ResponseEntity.ok(response);
    }


    // 🔹 Authenticate & Generate JWT Token
    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticate(@RequestBody Customer customer) {
        try {
            System.out.println("Received username: " + customer.getUsername());
            System.out.println("Received password: " + customer.getPassword());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword())
            );

            System.out.println("Authentication successful!");

            UserDetails userDetails = securityService.loadUserByUsername(customer.getUsername());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            System.out.println("Generated Token: " + token);

            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
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
