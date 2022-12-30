package pl.itj.dev.yourmechaniccrm.view.components.google.chart;

import com.vaadin.flow.internal.JsonUtils;
import elemental.json.JsonValue;

import java.util.HashMap;

public class GoogleChartOptions {

    private String title;

    private Integer fontSize = 12;

    private GoogleChartOptions () {}

    public static GoogleChartOptions create() {
        return new GoogleChartOptions();
    }

    public GoogleChartOptions withTitle(String title) {
        this.title = title;
        return this;
    }

    public GoogleChartOptions withFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public JsonValue toJsonValue() {
        var properties = new HashMap<String, Object>();
        properties.put("title", title);

        if (fontSize > 0) {
            properties.put("fontSize", fontSize);
        }

        return JsonUtils.mapToJson(properties);
    }

}
