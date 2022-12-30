package pl.itj.dev.yourmechaniccrm.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import pl.itj.dev.yourmechaniccrm.security.SecurityService;
import pl.itj.dev.yourmechaniccrm.view.customer.CustomersView;
import pl.itj.dev.yourmechaniccrm.view.dashboard.DashboardView;
import pl.itj.dev.yourmechaniccrm.view.order.OrdersView;
import pl.itj.dev.yourmechaniccrm.view.service.ServicesListView;

@Slf4j
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H3 logo = new H3("Your Mechanic CRM");
        logo.addClassNames("text-m", "m-0");

        Button logout = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.setHeight("50px");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        Icon dashboardIcon = new Icon("dashboard");
        dashboardIcon.addClassName(LumoUtility.Padding.Right.SMALL);
        dashboardLink.addClassName("spacing-s");
        dashboardLink.setHighlightCondition(HighlightConditions.sameLocation());
        dashboardLink.addComponentAsFirst(dashboardIcon);

        RouterLink listLink = new RouterLink("Customers", CustomersView.class);
        Icon customersIcon = new Icon("user");
        customersIcon.addClassName(LumoUtility.Padding.Right.SMALL);
        customersIcon.addClassName("spacing-s");
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        listLink.addComponentAsFirst(customersIcon);

        RouterLink servicesLink = new RouterLink("Services", ServicesListView.class);
        Icon servicesIcon = new Icon("barcode");
        servicesIcon.addClassName(LumoUtility.Padding.Right.SMALL);
        servicesIcon.addClassName("spacing-s");
        servicesLink.setHighlightCondition(HighlightConditions.sameLocation());
        servicesLink.addComponentAsFirst(servicesIcon);

        RouterLink ordersLink = new RouterLink("Orders", OrdersView.class);
        Icon ordersIcon = new Icon("records");
        ordersIcon.addClassName(LumoUtility.Padding.Right.SMALL);
        ordersIcon.addClassName("spacing-s");
        ordersLink.setHighlightCondition(HighlightConditions.sameLocation());
        ordersLink.addComponentAsFirst(ordersIcon);

        addToDrawer(new VerticalLayout(
                dashboardLink,
                listLink,
                servicesLink,
                ordersLink
        ));
    }
}
