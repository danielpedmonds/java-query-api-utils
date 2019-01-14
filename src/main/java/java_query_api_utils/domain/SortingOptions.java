package java_query_api_utils.domain;

public class SortingOptions {
    private String field;
    private String order;

    public SortingOptions() {}

    public SortingOptions(String field, String order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
