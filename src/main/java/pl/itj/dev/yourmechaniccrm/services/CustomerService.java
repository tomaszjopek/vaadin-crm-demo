package pl.itj.dev.yourmechaniccrm.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.itj.dev.yourmechaniccrm.data.entities.Customer;
import pl.itj.dev.yourmechaniccrm.data.entities.CustomerStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findById(String id);
    List<Customer> findAll();

    List<Customer> searchCustomer(String searchQuery, Pageable pageable);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    long countCustomers();

    Map<CustomerStatus, Long> getCustomersByStatusCount();
}
