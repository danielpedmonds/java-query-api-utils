package java_query_api_utils;

import java_query_api_utils.domain.QueryOptions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java_query_api_utils.ApiQueryParameters.SORTING_ASCENDING_PARAMETER;
import static java_query_api_utils.ApiQueryParameters.SORTING_DESCENDING_PARAMETER;
import static org.junit.Assert.assertEquals;

public class QueryOptionsSortingTests {

    private static final String SORTING_SORT_PARAMETER = "sort";

    QueryOptionsBuilder queryOptionsBuilder = new QueryOptionsBuilder();

    Map<String, String> parameters = new HashMap<>();

    @Test
    public void successfullyMapSorFields() {
        parameters.put(SORTING_SORT_PARAMETER, "firstName, lastName:asc, owningOrgType:desc");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals("firstName", sortingOptions.getSortingOptions().get(0).getField());
        assertEquals(null, sortingOptions.getSortingOptions().get(0).getOrder());
        assertEquals("lastName", sortingOptions.getSortingOptions().get(1).getField());
        assertEquals(SORTING_ASCENDING_PARAMETER, sortingOptions.getSortingOptions().get(1).getOrder());
        assertEquals("owningOrgType", sortingOptions.getSortingOptions().get(2).getField());
        assertEquals(SORTING_DESCENDING_PARAMETER, sortingOptions.getSortingOptions().get(2).getOrder());
    }

    @Test
    public void invalidOperatorWhenMappingSortParameters() {
        parameters.put(SORTING_SORT_PARAMETER, "firstName, lastName:asc, owningOrgType:invalid");

        try {
            QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            assertEquals("Parameter 'owningOrgType:invalid' is invalid, parameter must only contain operator :asc or :desc", e.getMessage());
        }
    }
}
