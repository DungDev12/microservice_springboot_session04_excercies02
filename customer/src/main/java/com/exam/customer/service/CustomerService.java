package com.exam.customer.service;

import com.exam.customer.dto.request.CustomerRequestDTO;
import com.exam.customer.dto.request.LoginRequestDTO;
import com.exam.customer.dto.response.CustomerResponseDTO;
import com.exam.customer.entity.Customer;
import com.exam.customer.exception.DuplicateResourceException;
import com.exam.customer.exception.InvalidCredentialsException;
import com.exam.customer.exception.ResourceNotFoundException;
import com.exam.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CustomerResponseDTO register(
            CustomerRequestDTO request
    ) {

        String email = request.email()
                .trim()
                .toLowerCase();

        if (customerRepository.existsByEmail((email))){
            throw new DuplicateResourceException(
                    "Email đã tồn tại: " + email
            );
        }

        String encodedPassword =
                passwordEncoder.encode(request.password());

        Customer customer = Customer.builder()
                .fullName(request.fullName().trim())
                .email(email)
                .password(encodedPassword)
                .build();

        Customer savedCustomer =
                customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Transactional
    public CustomerResponseDTO getById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy customer với id: " + id
                        )
                );

        return mapToResponse(customer);
    }

    @Transactional
    public List<CustomerResponseDTO> getAll() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public CustomerResponseDTO login(
            LoginRequestDTO request
    ) {

        String email = request.email()
                .trim()
                .toLowerCase();

        Customer customer = customerRepository
                .findByEmail(email).orElseThrow(() ->
                        new InvalidCredentialsException(
                                "email or password incorrect "
                        ));

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.password(),
                        customer.getPassword()
                );

        if (!passwordMatches) {
            throw new InvalidCredentialsException(
                    "email or password incorrect "
            );
        }

        return mapToResponse(customer);
    }

    @Transactional
    public CustomerResponseDTO update(
            Long id,
            CustomerRequestDTO request
    ) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy customer với id: " + id
                        )
                );

        String email = request.email()
                .trim()
                .toLowerCase();

        if (customerRepository.existsByEmailAndIdNot(
                email,
                id
        )) {
            throw new DuplicateResourceException(
                    "Email đã tồn tại: " + email
            );
        }

        customer.setFullName(request.fullName().trim());
        customer.setEmail(email);

        customer.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );

        Customer updatedCustomer =
                customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy customer với id: " + id
                        )
                );
        customerRepository.delete(customer);
    }

    private CustomerResponseDTO mapToResponse(
            Customer customer
    ) {

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail()
        );
    }
}
