package com.example.bank.service;

import com.example.bank.entity.Customer;
import com.example.bank.repo.CustomerRepo;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SecurityService implements UserDetailsService {

    private final CustomerRepo customerRepo;


    public UserDetails saveUser(Customer customer){
        return customerRepo.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepo.findByUsername(username).orElseThrow(()->new RuntimeException("user not found"));
    }

}
