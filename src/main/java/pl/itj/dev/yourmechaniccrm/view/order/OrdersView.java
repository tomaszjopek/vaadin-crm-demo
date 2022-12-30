package pl.itj.dev.yourmechaniccrm.view.order;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.itj.dev.yourmechaniccrm.services.OrderService;
import pl.itj.dev.yourmechaniccrm.view.MainLayout;

import javax.annotation.security.PermitAll;

@Route(value = "orders", layout = MainLayout.class)
@PageTitle(value = "Orders | Your Mechanic CRM")
@PermitAll
public class OrdersView extends VerticalLayout {

    private final OrderService orderService;

    public OrdersView(OrderService orderService) {
        this.orderService = orderService;
        add(new OrdersListView(orderService));
    }
}
