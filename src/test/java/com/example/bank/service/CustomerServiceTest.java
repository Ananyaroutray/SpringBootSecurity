package com.example.bank.service;

import com.example.bank.dto.CustomerDtO;
import com.example.bank.entity.Customer;
import com.example.bank.mapper.CustomerMapper;
import com.example.bank.repo.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock           // Mock the repository
    private CustomerRepo customerRepo;

    @InjectMocks  //Inject mocks into the service
    private CustomerServiceImpl customerService;


    @Test
    void testAddCustomer() {
        // Create mock input and output
        CustomerDtO inputDto = new CustomerDtO(1L, "John Doe", "john@example.com", 2000.0);
        Customer mockCustomer = new Customer(1L, "John Doe", "john@example.com", 2000.0, "123");
        CustomerDtO expectedDto = new CustomerDtO(1L, "John Doe", "john@example.com", 2000.0);

        // Mock static methods using Mockito.mockStatic
        try (MockedStatic<CustomerMapper> mockedMapper = Mockito.mockStatic(CustomerMapper.class)) {
            mockedMapper.when(() -> CustomerMapper.mapToEntity(inputDto)).thenReturn(mockCustomer);
            mockedMapper.when(() -> CustomerMapper.mapToDtO(mockCustomer)).thenReturn(expectedDto);

            // Mock repository behavior
            when(customerRepo.save(mockCustomer)).thenReturn(mockCustomer);

            // Call the method
            CustomerDtO actualDto = customerService.addCustomer(inputDto);

            // Assertions
            assertNotNull(actualDto);
            assertEquals(expectedDto.getId(), actualDto.getId());
            assertEquals(expectedDto.getName(), actualDto.getName());
            assertEquals(expectedDto.getEmail(), actualDto.getEmail());

            // Verify interactions
            verify(customerRepo, times(1)).save(mockCustomer);
            mockedMapper.verify(() -> CustomerMapper.mapToEntity(inputDto), times(1));
            mockedMapper.verify(() -> CustomerMapper.mapToDtO(mockCustomer), times(1));
        }
    }

}
