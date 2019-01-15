package java_query_api_utils;

import java_query_api_utils.domain.QueryOptions;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryOptionsFilteringTests {

    QueryOptionsBuilder queryOptionsBuilder = new QueryOptionsBuilder();

    Map<String, String> parameters = new LinkedHashMap<>();

    @Test
    public void successfullyMapFilterParameters() {
        parameters.put("fullName", "Daniel Edmonds");
        parameters.put("owningOrgType", "Some Organisation");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("fullName", sortingOptions.getFilterOptions().get(0).getField());
        assertEquals("Daniel Edmonds", sortingOptions.getFilterOptions().get(0).getValue());
        assertEquals("", sortingOptions.getFilterOptions().get(0).getOperator());

        assertEquals("owningOrgType", sortingOptions.getFilterOptions().get(1).getField());
        assertEquals("Some Organisation", sortingOptions.getFilterOptions().get(1).getValue());
        assertEquals("", sortingOptions.getFilterOptions().get(1).getOperator());
    }

    @Test
    public void successfullyMapFilterParametersEqualsOperator() {
        parameters.put("fullName", "Daniel Edmonds:eq");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("fullName", sortingOptions.getFilterOptions().get(0).getField());
        assertEquals("Daniel Edmonds", sortingOptions.getFilterOptions().get(0).getValue());
        assertEquals("eq", sortingOptions.getFilterOptions().get(0).getOperator());
    }

    @Test
    public void successfullyMapFilterParametersLikeOperator() {
        parameters.put("fullName", "Daniel Edmonds:lk");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("fullName", sortingOptions.getFilterOptions().get(0).getField());
        assertEquals("Daniel Edmonds", sortingOptions.getFilterOptions().get(0).getValue());
        assertEquals("lk", sortingOptions.getFilterOptions().get(0).getOperator());
    }

    @Test
    public void successfullyMapFilterParametersNotEqualsOperator() {
        parameters.put("fullName", "Daniel Edmonds:neq");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("fullName", sortingOptions.getFilterOptions().get(0).getField());
        assertEquals("Daniel Edmonds", sortingOptions.getFilterOptions().get(0).getValue());
        assertEquals("neq", sortingOptions.getFilterOptions().get(0).getOperator());
    }

    @Test
    public void successfullyMapFilterParametersGreaterThanOperator() {
        parameters.put("fullName", "Daniel Edmonds:gt");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("fullName", sortingOptions.getFilterOptions().get(0).getField());
        assertEquals("Daniel Edmonds", sortingOptions.getFilterOptions().get(0).getValue());
        assertEquals("gt", sortingOptions.getFilterOptions().get(0).getOperator());
    }

    @Test
    public void successfullyMapFilterParametersGreaterThanEqualToOperator() {
        parameters.put("fullName", "Daniel Edmonds:gte");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("fullName", sortingOptions.getFilterOptions().get(0).getField());
        assertEquals("Daniel Edmonds", sortingOptions.getFilterOptions().get(0).getValue());
        assertEquals("gte", sortingOptions.getFilterOptions().get(0).getOperator());
    }

    @Test
    public void successfullyMapFilterParametersLessThanOperator() {
        parameters.put("fullName", "Daniel Edmonds:lt");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("fullName", sortingOptions.getFilterOptions().get(0).getField());
        assertEquals("Daniel Edmonds", sortingOptions.getFilterOptions().get(0).getValue());
        assertEquals("lt", sortingOptions.getFilterOptions().get(0).getOperator());
    }

    @Test
    public void successfullyMapFilterParametersLessThanEqualToOperator() {
        parameters.put("fullName", "Daniel Edmonds:lte");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("fullName", sortingOptions.getFilterOptions().get(0).getField());
        assertEquals("Daniel Edmonds", sortingOptions.getFilterOptions().get(0).getValue());
        assertEquals("lte", sortingOptions.getFilterOptions().get(0).getOperator());
    }

    @Test
    public void invalidOperatorForParameter() {
        parameters.put("fullName", "Daniel Edmonds:invalid");

        try {
            QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid operator passed in parameter 'Daniel Edmonds:invalid'", e.getMessage());
        }
    }

}
