package pl.itj.dev.yourmechaniccrm.view.customer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.PageRequest;
import pl.itj.dev.yourmechaniccrm.data.entities.Customer;
import pl.itj.dev.yourmechaniccrm.services.CustomerService;
import pl.itj.dev.yourmechaniccrm.view.MainLayout;

import javax.annotation.security.PermitAll;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle(value = "Customers | Your Mechanic CRM")
@PermitAll
public class CustomersView extends VerticalLayout {
    private final Grid<Customer> grid = new Grid<>(Customer.class);
    private final TextField filterText = new TextField();

    private CustomerForm customerForm;

    private final CustomerService customerService;

    public CustomersView(CustomerService customerService) {
        this.customerService = customerService;

        addClassName("customers-list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateCustomersList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("customers-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(new LocalDateTimeRenderer<>(
                Customer::getCreatedDate,
                "dd/MM HH:mm:ss")
        ).setHeader("Created date");
        grid.addColumn(new LocalDateTimeRenderer<>(
                Customer::getLastModifiedDate,
                "dd/MM HH:mm:ss")
        ).setHeader("Last modified date");

        grid.addColumn(createStatusComponentRenderer()).setHeader("Status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editCustomer(event.getValue()));
    }

    private static final SerializableBiConsumer<Span, Customer> statusComponentUpdater = (span, customer) -> {
        String theme = String.format("badge %s", calculateThemeClass(customer));
        span.getElement().setAttribute("theme", theme);
        span.setText(customer.getStatus().name());
    };

    private static String calculateThemeClass(Customer customer) {
        return switch (customer.getStatus()) {
            case ACTIVE -> "success";
            case INACTIVE -> "error";
            default -> "";
        };
    }

    private static ComponentRenderer<Span, Customer> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, customerForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, customerForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        customerForm = new CustomerForm();
        customerForm.setWidth("25em");
        customerForm.addListener(CustomerForm.SaveEvent.class, this::saveCustomer);
        customerForm.addListener(CustomerForm.DeleteEvent.class, this::deleteCustomer);
        customerForm.addListener(CustomerForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveCustomer(CustomerForm.SaveEvent event) {
        customerService.saveCustomer(event.getCustomer());
        updateCustomersList();
        closeEditor();
    }

    private void deleteCustomer(CustomerForm.DeleteEvent event) {
        customerService.deleteCustomer(event.getCustomer());
        updateCustomersList();
        closeEditor();
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCustomersList());

        Button addCustomerButton = new Button("Add customer");
        addCustomerButton.addClickListener(event -> addCustomer());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editCustomer(Customer customer) {
        if (customer == null) {
            closeEditor();
        } else {
            customerForm.setCustomer(customer);
            customerForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        customerForm.setCustomer(null);
        customerForm.setVisible(false);
        removeClassName("editing");
    }

    private void addCustomer() {
        grid.asSingleSelect().clear();
        editCustomer(new Customer());
    }

    private void updateCustomersList() {
        grid.setItems(
                query -> customerService.searchCustomer(
                        filterText.getValue(),
                        PageRequest.of(query.getPage(), query.getPageSize())
                ).stream()
        );
    }
}
