package pl.itj.dev.yourmechaniccrm.services;

import org.springframework.data.domain.Pageable;
import pl.itj.dev.yourmechaniccrm.data.entities.Order;

import java.util.stream.Stream;

public interface OrderService {

    Stream<Order> findCustomerOrders(String customerId, Pageable pageable);

    Stream<Order> findAll(Pageable pageable);

}
