package pl.itj.dev.yourmechaniccrm.data.entities;

import lombok.*;
import org.hibernate.Hibernate;
import pl.itj.dev.yourmechaniccrm.data.entities.base.BaseAuditableEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "INVOICES")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Invoice extends BaseAuditableEntity {

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Lob
    private byte[] content;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Invoice invoice = (Invoice) o;
        return getId() != null && Objects.equals(getId(), invoice.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
