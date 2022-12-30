package pl.itj.dev.yourmechaniccrm.services.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.itj.dev.yourmechaniccrm.data.entities.Order;
import pl.itj.dev.yourmechaniccrm.repositories.OrderRepository;
import pl.itj.dev.yourmechaniccrm.services.OrderService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Stream<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).stream();
    }

    @Override
    public Stream<Order> findCustomerOrders(String customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(UUID.fromString(customerId), pageable).stream();
    }
}
