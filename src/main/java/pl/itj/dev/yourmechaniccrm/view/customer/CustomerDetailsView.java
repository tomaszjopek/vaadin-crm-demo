package pl.itj.dev.yourmechaniccrm.view.customer;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import pl.itj.dev.yourmechaniccrm.data.entities.Customer;
import pl.itj.dev.yourmechaniccrm.data.entities.CustomerStatus;
import pl.itj.dev.yourmechaniccrm.services.CustomerService;
import pl.itj.dev.yourmechaniccrm.services.OrderService;
import pl.itj.dev.yourmechaniccrm.view.MainLayout;
import pl.itj.dev.yourmechaniccrm.view.order.OrdersListView;

import javax.annotation.security.PermitAll;
import java.util.List;

@Route(value = "customers/:customerId", layout = MainLayout.class)
@PageTitle(value = "Customer Details | Your Mechanic CRM")
@PermitAll
@Slf4j
public class CustomerDetailsView extends VerticalLayout implements BeforeEnterObserver {

    private final CustomerService customerService;

    private final OrderService orderService;
    private Customer customer;

    private final TextField firstName = new TextField("First name");

    private final TextField lastName = new TextField("Last name");

    private final EmailField email = new EmailField("Email");

    private final Image customerImage = new Image("https://via.placeholder.com/150", "Customer image");

    private final ComboBox<CustomerStatus> customerStatusComboBox = new ComboBox<>("Status", CustomerStatus.values());

    private final List<HasValueAndElement<?, ?>> editableComponents = List.of(firstName, lastName, email, customerStatusComboBox);

    private final Button editButton = new Button("Edit");

    private final Button saveButton = new Button("Save");

    private boolean isEditMode = false;

    public CustomerDetailsView(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
        addClassName("customer-details-view");
        addDetailsHeader();
        addFieldsSection();
        addCustomerOrdersList();
    }

    private void addDetailsHeader() {
        HorizontalLayout detailsWithActionButtons = new HorizontalLayout();
        detailsWithActionButtons.setJustifyContentMode(JustifyContentMode.BETWEEN);
        detailsWithActionButtons.setAlignItems(Alignment.CENTER);
        detailsWithActionButtons.setWidthFull();

        H2 detailsHeader = new H2("Details");
        HorizontalLayout buttonsGroup = new HorizontalLayout(editButton);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(e -> {
            log.info(String.valueOf(customer));
            buttonsGroup.remove(saveButton);
        });

        editButton.addClickListener(e -> {
            isEditMode = true;
            editableComponents.forEach(component -> component.setReadOnly(!isEditMode));
            buttonsGroup.add(saveButton);
        });

        detailsWithActionButtons.add(detailsHeader, buttonsGroup);
        add(detailsWithActionButtons);
    }

    private void addFieldsSection() {
        HorizontalLayout imageWithFields = new HorizontalLayout(customerImage, firstName, lastName, email, customerStatusComboBox);
        add(imageWithFields);
    }

    private void setupFields() {
        firstName.setValue(customer.getFirstName());
        lastName.setValue(customer.getLastName());
        email.setValue(customer.getEmail());
        customerStatusComboBox.setValue(customer.getStatus());

        editableComponents.forEach(component -> component.setReadOnly(!isEditMode));
    }

    private void addCustomerOrdersList() {
        OrdersListView ordersListView = new OrdersListView(customer, orderService);
        add(ordersListView);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        var customerId  = beforeEnterEvent.getRouteParameters().get("customerId").orElseThrow();
        customer = customerService.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        setupFields();
    }
}
