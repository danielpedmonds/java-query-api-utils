package java_query_api_utils;

import java_query_api_utils.domain.QueryOptions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryOptionsPaginationTests {

    public static final String PAGINATION_PAGE_PARAMETER = "page";
    public static final String PAGINATION_SIZE_PARAMETER = "size";

    QueryOptionsBuilder queryOptionsBuilder = new QueryOptionsBuilder();

    Map<String, String> parameters = new HashMap<>();

    @Test
    public void successfullyMapPagination() {
        parameters.put(PAGINATION_PAGE_PARAMETER, "1");
        parameters.put(PAGINATION_SIZE_PARAMETER, "25");
        QueryOptions sortingOptions = queryOptionsBuilder.mapToEnquiryOptionsObject(parameters);

        assertEquals(Integer.valueOf("1"), sortingOptions.getPaginationOptions().getPageNumber());
        assertEquals(Integer.valueOf("25"), sortingOptions.getPaginationOptions().getPageSize());
    }

}
