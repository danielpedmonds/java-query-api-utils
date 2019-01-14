package java_query_api_utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryApiValidatorSortingTests {

    private static final String SORTING_SORT_PARAMETER = "sort";

    private static final String [] VALID_QUERY_FIELDS = {"fullName", "owningOrgType", "userId"};

    QueryApiValidator queryApiValidator = new QueryApiValidator();

    Map<String, String> requestParam = new HashMap<>();

    @Test
    public void validateSortingParametersValid() {
        requestParam.put(SORTING_SORT_PARAMETER, "fullName, owningOrgType:asc, userId:desc");
        queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
    }

    @Test
    public void validateSortingParametersEmptyReturnsError(){
        requestParam.put(SORTING_SORT_PARAMETER, "");
        try {
            queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
            throw new IllegalArgumentException();
        } catch (Exception e) {
            assertEquals( "Parameter 'sort' should have a value if passed", e.getMessage());
        }
    }

    @Test
    public void validateSortingParametersInvalidParameterReturnsError(){
        requestParam.put(SORTING_SORT_PARAMETER, "fullName, someInvalidField");
        try {
            queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
            throw new IllegalArgumentException();
        } catch (Exception e) {
            assertEquals( "Passed value 'someInvalidField' is not valid field to be filtered", e.getMessage());
        }
    }

    @Test
    public void validateSortingParametersInvalidOperatorReturnsError(){
        requestParam.put(SORTING_SORT_PARAMETER, "fullName, owningOrgType:abcd");
        try {
            queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
            throw new IllegalArgumentException();
        } catch (Exception e) {
            assertEquals( "Parameter 'sort' can only contain operator :asc or :desc", e.getMessage());
        }
    }
}
