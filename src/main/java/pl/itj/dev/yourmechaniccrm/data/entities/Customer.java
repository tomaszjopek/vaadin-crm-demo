package pl.itj.dev.yourmechaniccrm.data.entities;

import lombok.*;
import org.hibernate.Hibernate;
import pl.itj.dev.yourmechaniccrm.data.entities.base.BaseAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMERS")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer extends BaseAuditableEntity implements EntityContext {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.NEW;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}