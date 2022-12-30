package pl.itj.dev.yourmechaniccrm.services.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.itj.dev.yourmechaniccrm.repositories.ServiceRepository;
import pl.itj.dev.yourmechaniccrm.services.ServiceService;

import java.util.stream.Stream;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Stream<pl.itj.dev.yourmechaniccrm.data.entities.Service> findAll(Pageable pageable) {
        return serviceRepository.findAll(pageable).stream();
    }
}
