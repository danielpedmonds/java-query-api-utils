package java_query_api_utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryApiValidatorFilteringTests {

    private static final String [] VALID_QUERY_FIELDS = {"fullName", "owningOrgType", "userId"};

    QueryApiValidator queryApiValidator = new QueryApiValidator();

    Map<String, String> requestParam = new HashMap<>();

    @Test
    public void validateFilterableFieldIsValid() {
        requestParam.put("fullName", "Daniel Edmonds");
        requestParam.put("owningOrgType", "Some Organisation");
        queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
    }

    @Test
    public void validateFilterableFieldIsInvalid() {
        requestParam.put("fullName", "Daniel Edmonds");
        requestParam.put("invalidField", "Invalid value");
        try {
            queryApiValidator.runDefaultQueryValidation(VALID_QUERY_FIELDS, requestParam);
            throw new IllegalArgumentException();
        } catch (Exception e) {
            assertEquals("Parameter 'invalidField' is not a valid queryable field", e.getMessage());
        }
    }
}
