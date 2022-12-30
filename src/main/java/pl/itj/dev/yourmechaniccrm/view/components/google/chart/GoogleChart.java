package pl.itj.dev.yourmechaniccrm.view.components.google.chart;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.internal.JsonUtils;

import java.util.List;

@Tag("google-chart")
@NpmPackage(value = "@google-web-components/google-chart", version = "5.0.2")
@JsModule("@google-web-components/google-chart/google-chart.js")
public class GoogleChart extends Component {

    private static final PropertyDescriptor<String, String> typeProperty = PropertyDescriptors.propertyWithDefault("type", "");

    private List<GoogleChartColumnDef> columns = List.of();

    private List<Object[]> rows = List.of();

    private GoogleChartOptions options;

    public GoogleChart(GoogleChartType chartType, GoogleChartOptions options) {
        typeProperty.set(this, chartType.name().toLowerCase());
        this.options = options;
        getElement().setPropertyJson("options", options.toJsonValue());
    }

    public void setType(String type) {
        typeProperty.set(this, type);
    }

    public String getType() {
        return typeProperty.get(this);
    }

    public List<GoogleChartColumnDef> getColumns() {
        return columns;
    }

    public void setColumns(List<GoogleChartColumnDef> columns) {
        this.columns = columns;
        getElement().setPropertyJson("cols", JsonUtils.listToJson(this.columns));
    }

    public List<Object[]> getRows() {
        return rows;
    }

    public void setRows(List<Object[]> rows) {
        getElement().setPropertyJson("rows", JsonUtils.listToJson(rows));
        this.rows = rows;
    }
}
