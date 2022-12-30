package pl.itj.dev.yourmechaniccrm.data.entities;

import lombok.*;
import org.hibernate.Hibernate;
import pl.itj.dev.yourmechaniccrm.data.entities.base.BaseAuditableEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ORDERS", indexes = {
        @Index(name = "idx_order_customer_id", columnList = "customer_id")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Order extends BaseAuditableEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private LocalDateTime requestedExecutionDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
