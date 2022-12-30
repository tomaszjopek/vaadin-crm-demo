package pl.itj.dev.yourmechaniccrm.services.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.itj.dev.yourmechaniccrm.data.aggregations.CustomerCount;
import pl.itj.dev.yourmechaniccrm.data.entities.Customer;
import pl.itj.dev.yourmechaniccrm.data.entities.CustomerStatus;
import pl.itj.dev.yourmechaniccrm.repositories.CustomerRepository;
import pl.itj.dev.yourmechaniccrm.services.CustomerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toMap;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Customer> findById(String id) {
        return customerRepository.findById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Customer> searchCustomer(String searchQuery, Pageable pageable) {
        if (searchQuery == null || searchQuery.isEmpty()) {
            return customerRepository.findAll(pageable).getContent();
        }

        return customerRepository.findByFirstNameLikeOrLastNameLike(searchQuery, pageable);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public long countCustomers() {
        return customerRepository.count();
    }

    @Override
    public Map<CustomerStatus, Long> getCustomersByStatusCount() {
        return customerRepository.findCustomersByStatusCount()
                .stream()
                .collect(toMap(CustomerCount::getStatus, CustomerCount::getCount));
    }
}
