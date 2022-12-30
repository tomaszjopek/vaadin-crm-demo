package pl.itj.dev.yourmechaniccrm.data.entities;

import lombok.*;
import org.hibernate.Hibernate;
import pl.itj.dev.yourmechaniccrm.data.entities.base.BaseAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "SERVICES")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Service extends BaseAuditableEntity {

    private String name;

    private String description;

    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Service service = (Service) o;
        return getId() != null && Objects.equals(getId(), service.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}