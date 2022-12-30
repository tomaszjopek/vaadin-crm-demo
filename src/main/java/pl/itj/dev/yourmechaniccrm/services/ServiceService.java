package pl.itj.dev.yourmechaniccrm.services;

import org.springframework.data.domain.Pageable;
import pl.itj.dev.yourmechaniccrm.data.entities.Service;

import java.util.stream.Stream;

public interface ServiceService {

    Stream<Service> findAll(Pageable pageable);

}
