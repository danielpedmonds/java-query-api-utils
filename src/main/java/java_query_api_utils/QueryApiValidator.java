package java_query_api_utils;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java_query_api_utils.ApiQueryParameters.*;

public class QueryApiValidator {

    public void runDefaultQueryValidation(String[] validFields, Map<String, String> requestParam) throws IllegalArgumentException {

        List<String> validationErrorList = new ArrayList<>();

        validateSortParameter(validFields, requestParam, validationErrorList);

        validatePagination(requestParam, validationErrorList);

        validateFilterParameters(validFields, requestParam, validationErrorList);

        if (!validationErrorList.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", validationErrorList));
        }
    }

    /**
     * Validates the sorting field, which will be a comma separated string of fields,
     * with optional :asc (ascending order sorting) or :desc (descending order sorting) property,
     * defaulting to ascending order when neither is specified.
     *
     * @param requestParams
     * @param validationErrorList
     * @return validationErrorList
     */
    private List<String> validateSortParameter(String[] validFields, Map<String, String> requestParams, List<String> validationErrorList) {
        if (requestParams.containsKey(SORTING_SORT_PARAMETER)) {
            String sortingSortParam = requestParams.get(SORTING_SORT_PARAMETER);

            if (sortingSortParam.isEmpty()) {
                validationErrorList.add("Parameter '" + SORTING_SORT_PARAMETER + "' should have a value if passed");
                return validationErrorList;
            }

            String[] strParts = sortingSortParam.split("\\s*,\\s*");
            ArrayList<String> aList = new ArrayList<String>(Arrays.asList(strParts));

            for (String str : aList) {
                String fieldname = str.replace(COLON_OPERATOR + SORTING_ASCENDING_PARAMETER, "")
                        .replace(COLON_OPERATOR + SORTING_DESCENDING_PARAMETER, "");

                if (fieldname.contains(COLON_OPERATOR)) {
                    validationErrorList.add("Parameter '" + SORTING_SORT_PARAMETER + "' can only contain operator :"
                            + SORTING_ASCENDING_PARAMETER + " or :" + SORTING_DESCENDING_PARAMETER);
                } else if (!ArrayUtils.contains(validFields, fieldname)) {
                    validationErrorList.add("Passed value '" + str + "' is not valid field to be filtered");
                }
            }
        }
        return validationErrorList;
    }

    /**
     * Validates that both page & size parameters are present,
     * both fields of which are validated to be integers greater than 0.
     *
     * @param requestParams
     * @param validationErrorList
     * @return validationErrorList
     */
    private List<String> validatePagination(Map<String, String> requestParams, List<String> validationErrorList) {
        if (requestParams.containsKey(PAGINATION_PAGE_PARAMETER) ^ requestParams.containsKey(PAGINATION_SIZE_PARAMETER)) {
            validationErrorList.add("Parameter '" + PAGINATION_PAGE_PARAMETER + "' & '"
                    + PAGINATION_SIZE_PARAMETER + "' are both required for pagination");
        } else if (requestParams.containsKey(PAGINATION_PAGE_PARAMETER) && requestParams.containsKey(PAGINATION_SIZE_PARAMETER)){
            String paginationPageParam = requestParams.get(PAGINATION_PAGE_PARAMETER);
            if (!Pattern.matches("^[1-9].*", paginationPageParam)) {
                validationErrorList.add("Parameter '" + PAGINATION_PAGE_PARAMETER + "' should be a whole number > 0");
            }
            String paginationSizeParam = requestParams.get(PAGINATION_SIZE_PARAMETER);
            if (!Pattern.matches("^[1-9].*", paginationSizeParam)) {
                validationErrorList.add("Parameter '" + PAGINATION_SIZE_PARAMETER + "' should be a whole number > 0");
            }
        }
        return validationErrorList;
    }

    private List<String> validateFilterParameters(String[] validFields, Map<String, String> requestParams, List<String> validationErrorList) {
        List<String> validFieldsList = Arrays.asList(validFields);

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            if (!validFieldsList.contains(entry.getKey()) && !entry.getKey().equals(SORTING_SORT_PARAMETER) &&
                    !entry.getKey().equals(PAGINATION_PAGE_PARAMETER)&& !entry.getKey().equals(PAGINATION_SIZE_PARAMETER)) {
                validationErrorList.add("Parameter '" + entry.getKey() + "' is not a valid queryable field");
            }
        }

        return validationErrorList;
    }
}
