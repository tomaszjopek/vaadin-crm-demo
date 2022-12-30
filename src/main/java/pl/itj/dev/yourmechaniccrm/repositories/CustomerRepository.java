package pl.itj.dev.yourmechaniccrm.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.itj.dev.yourmechaniccrm.data.aggregations.CustomerCount;
import pl.itj.dev.yourmechaniccrm.data.entities.Customer;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("""
            select c 
            from Customer c 
            where lower(c.firstName) like lower(concat('%', :search, '%'))
             or lower(c.lastName) like lower(concat('%', :search, '%'))
            """)
    List<Customer> findByFirstNameLikeOrLastNameLike(@Param("search") String searchQuery, Pageable pageable);

    @Query("""
            SELECT new pl.itj.dev.yourmechaniccrm.data.aggregations.CustomerCount(c.status, COUNT(c.status))
            FROM Customer AS c GROUP BY c.status
            """)
    List<CustomerCount> findCustomersByStatusCount();
}
