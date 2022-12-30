package pl.itj.dev.yourmechaniccrm.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import pl.itj.dev.yourmechaniccrm.security.SecurityService;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    private SecurityService securityService;

    public AuditorAwareImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(securityService.getAuthenticatedUser().getUsername());
    }
}
