package pl.itj.dev.yourmechaniccrm.view.components.google.chart;

import com.vaadin.flow.internal.JsonUtils;
import elemental.json.JsonObject;

public record GoogleChartColumnDef(String label, String type) {

    public JsonObject toJsonObject() {
        return JsonUtils.beanToJson(this);
    }

}
