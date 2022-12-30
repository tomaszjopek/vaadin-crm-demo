package pl.itj.dev.yourmechaniccrm.view.order;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.data.domain.PageRequest;
import pl.itj.dev.yourmechaniccrm.data.entities.Customer;
import pl.itj.dev.yourmechaniccrm.data.entities.EntityContext;
import pl.itj.dev.yourmechaniccrm.data.entities.Order;
import pl.itj.dev.yourmechaniccrm.services.OrderService;

public class OrdersListView extends VerticalLayout {

    private Grid<Order> ordersGrid = new Grid<>(Order.class);

    private EntityContext entityContext;

    private OrderService orderService;

    public OrdersListView(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrdersListView(EntityContext entityContext, OrderService orderService) {
        this.entityContext = entityContext;
        this.orderService = orderService;

        addClassName("orders-list-view");
        setSizeFull();
        configureListHeader();
        configureGrid();
        loadOrders();
    }

    private void configureListHeader() {
        H2 header = new H2("Orders");
        add(header);
    }

    private void configureGrid() {
        ordersGrid.setSizeFull();
        ordersGrid.setColumns("id");
        ordersGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        add(ordersGrid);
    }

    private void loadOrders() {
        if (entityContext instanceof Customer) {
            ordersGrid.setItems(q -> orderService.findCustomerOrders(entityContext.getId().toString(), PageRequest.of(q.getPage(), q.getPageSize())));
        } else {
            ordersGrid.setItems(q -> orderService.findAll(PageRequest.of(q.getPage(), q.getPageSize())));
        }
    }
}
