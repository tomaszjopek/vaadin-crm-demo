package pl.itj.dev.yourmechaniccrm.view.service;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.PageRequest;
import pl.itj.dev.yourmechaniccrm.data.entities.Service;
import pl.itj.dev.yourmechaniccrm.services.ServiceService;
import pl.itj.dev.yourmechaniccrm.view.MainLayout;

import javax.annotation.security.PermitAll;

@Route(value = "services", layout = MainLayout.class)
@PageTitle(value = "Services | Your Mechanic CRM")
@PermitAll
public class ServicesListView extends VerticalLayout {
    private Grid<Service> servicesGrid = new Grid<>(Service.class);

    private ServiceService serviceService;

    public ServicesListView(ServiceService serviceService) {
        this.serviceService = serviceService;

        addClassName("services-list-view");
        setSizeFull();
        configureListHeader();
        configureGrid();
        loadServices();
    }

    private void configureListHeader() {
        H2 header = new H2("Services");
        add(header);
    }

    private void configureGrid() {
        servicesGrid.setSizeFull();
        servicesGrid.setColumns("id", "name", "price");
        servicesGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        add(servicesGrid);
    }

    private void loadServices() {
        servicesGrid.setItems(query -> serviceService.findAll(PageRequest.of(query.getPage(), query.getPageSize())));
    }
}
