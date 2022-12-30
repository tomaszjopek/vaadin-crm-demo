package pl.itj.dev.yourmechaniccrm.view.dashboard;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import pl.itj.dev.yourmechaniccrm.services.CustomerService;
import pl.itj.dev.yourmechaniccrm.view.MainLayout;
import pl.itj.dev.yourmechaniccrm.view.components.google.chart.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Dashboard | Your Mechanic CRM")
@PermitAll
public class DashboardView extends VerticalLayout {

    private final CustomerService customerService;

    public DashboardView(CustomerService customerService) {
        this.customerService = customerService;

        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        add(new H2("Dashboard"));
        addCustomersByStatusesPieChart();
    }

    private void addCustomersByStatusesPieChart() {
        var charTitle = prepareCustomersByStatusesChartTitle();
        var chartOptions = GoogleChartOptions.create()
                .withTitle(charTitle);

        GoogleChart googleChart = new GoogleChart(GoogleChartType.PIE, chartOptions);
        prepareCustomersByStatusesChartColumns(googleChart);
        prepareCustomersByStatusesChartRows(googleChart);
        add(googleChart);
    }

    private String prepareCustomersByStatusesChartTitle() {
        return String.format("Customers(all = %d) by statuses", customerService.countCustomers());
    }

    private static void prepareCustomersByStatusesChartColumns(GoogleChart googleChart) {
        googleChart.setColumns(List.of(
                new GoogleChartColumnDef("Statuses", ColumnType.STRING.toString().toLowerCase()),
                new GoogleChartColumnDef("Count", ColumnType.NUMBER.toString().toLowerCase())
        ));
    }

    private void prepareCustomersByStatusesChartRows(GoogleChart googleChart) {
        var rows = customerService.getCustomersByStatusCount()
                .entrySet()
                .stream()
                .map(entry -> new Object[] { entry.getKey().toString(), entry.getValue() })
                .toList();

        googleChart.setRows(rows);
    }

}
