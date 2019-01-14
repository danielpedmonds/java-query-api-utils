package java_query_api_utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryApiValidatorPaginationTests {

    public static final String PAGINATION_PAGE_PARAMETER = "page";
    public static final String PAGINATION_SIZE_PARAMETER = "size";

    private static final String [] VALID_QUERY_FIELDS = {};

    QueryApiValidator queryApiValidator = new QueryApiValidator();

    Map<String, String> requestParam = new HashMap<>();

    @Test
    public void validatePaginationParametersValid() {
        requestParam.put(PAGINATION_PAGE_PARAMETER, "1");
        requestParam.put(PAGINATION_SIZE_PARAMETER, "25");
        queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
    }

    @Test
    public void validatePaginationParametersInvalidPageNumber() {
        requestParam.put(PAGINATION_PAGE_PARAMETER, "0");
        requestParam.put(PAGINATION_SIZE_PARAMETER, "25");
        try {
            queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
            throw new IllegalArgumentException();
        } catch (Exception e) {
            assertEquals( "Parameter 'page' should be a whole number > 0", e.getMessage());
        }
    }

    @Test
    public void validatePaginationParametersInvalidPageSize() {
        requestParam.put(PAGINATION_PAGE_PARAMETER, "1");
        requestParam.put(PAGINATION_SIZE_PARAMETER, "@#$%");
        try {
            queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
            throw new IllegalArgumentException();
        } catch (Exception e) {
            assertEquals( "Parameter 'size' should be a whole number > 0", e.getMessage());
        }
    }

    @Test
    public void validatePaginationParametersShouldBothBePresent() {
        requestParam.put(PAGINATION_PAGE_PARAMETER, "1");
        try {
            queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
            throw new IllegalArgumentException();
        } catch (Exception e) {
            assertEquals( "Parameter 'page' & 'size' are both required for pagination", e.getMessage());
        }
    }

    @Test
    public void validatePaginationParametersShouldPassWhenNeitherPresent() {
        queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
    }
}
