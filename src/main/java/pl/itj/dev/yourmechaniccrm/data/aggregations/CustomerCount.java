package pl.itj.dev.yourmechaniccrm.data.aggregations;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.itj.dev.yourmechaniccrm.data.entities.CustomerStatus;

@Data
@AllArgsConstructor
public class CustomerCount {

    private CustomerStatus status;

    private long count;
}
