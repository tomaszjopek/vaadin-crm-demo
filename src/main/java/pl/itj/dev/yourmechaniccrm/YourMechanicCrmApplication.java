package pl.itj.dev.yourmechaniccrm;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableTransactionManagement
@Theme("customtheme")
public class YourMechanicCrmApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(YourMechanicCrmApplication.class, args);
	}

}
