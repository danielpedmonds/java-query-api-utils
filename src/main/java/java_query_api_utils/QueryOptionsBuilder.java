package java_query_api_utils;

import java_query_api_utils.domain.QueryOptions;
import java_query_api_utils.domain.FilterOptions;
import java_query_api_utils.domain.PaginationOptions;
import java_query_api_utils.domain.SortingOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java_query_api_utils.ApiQueryParameters.*;

public class QueryOptionsBuilder {

    /**
     * This class is used to map query parameters to QueryOptions object
     * containing PaginationOptions, QueryOptions and SortingOptions objects.
     * @param parameters
     * @return QueryOptions
     */

    public QueryOptions mapToEnquiryOptionsObject(Map<String, String> parameters) {
        QueryOptions queryOptions = new QueryOptions();
        if (parameters.get(PAGINATION_PAGE_PARAMETER) != null && parameters.get(PAGINATION_SIZE_PARAMETER) != null) {
            queryOptions.setPaginationOptions(deserializeToPaginationOptions(parameters.get(PAGINATION_PAGE_PARAMETER), parameters.get(PAGINATION_SIZE_PARAMETER)));
        }
        queryOptions.setFilterOptions(deserializeToFilterOptions(parameters));
        if (parameters.get(SORTING_SORT_PARAMETER) != null) {
            queryOptions.setSortingOptions(deserializeToSortingOptions(parameters.get(SORTING_SORT_PARAMETER)));
        }

        return queryOptions;
    }

    private PaginationOptions deserializeToPaginationOptions(String pageNumber, String pageSize) {
        PaginationOptions paginationOptions = new PaginationOptions();
        paginationOptions.setPageNumber(Integer.valueOf(pageNumber));
        paginationOptions.setPageSize(Integer.valueOf(pageSize));
        return paginationOptions;
    }

    private List<FilterOptions> deserializeToFilterOptions(Map<String, String> parameters) {
        List<FilterOptions> filterOptionsList = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {

            if (!entry.getKey().equals(PAGINATION_PAGE_PARAMETER)
                    && !entry.getKey().equals(PAGINATION_SIZE_PARAMETER)
                    && !entry.getKey().equals(SORTING_SORT_PARAMETER)) {

                String value = entry.getValue();
                String operator = "";

                if (value.contains(COLON_OPERATOR + FILTERING_EQUAL_OPERATOR)){
                    operator = FILTERING_EQUAL_OPERATOR;
                    value = value.replace(COLON_OPERATOR + FILTERING_EQUAL_OPERATOR, "");
                } else if (value.contains(COLON_OPERATOR + FILTERING_LIKE_OPERATOR)) {
                    operator = FILTERING_LIKE_OPERATOR;
                    value = value.replace(COLON_OPERATOR + FILTERING_LIKE_OPERATOR, "");
                } else if (value.contains(COLON_OPERATOR + FILTERING_INEQUALITY_OPERATOR)) {
                    operator = FILTERING_INEQUALITY_OPERATOR;
                    value = value.replace(COLON_OPERATOR + FILTERING_INEQUALITY_OPERATOR, "");
                } else if (value.contains(COLON_OPERATOR + FILTERING_GREATER_THAN_OR_EQUAL_TO_OPERATOR)) {
                    operator = FILTERING_GREATER_THAN_OR_EQUAL_TO_OPERATOR;
                    value = value.replace(COLON_OPERATOR + FILTERING_GREATER_THAN_OR_EQUAL_TO_OPERATOR, "");
                } else if (value.contains(COLON_OPERATOR + FILTERING_GREATER_THAN_OPERATOR)) {
                    operator = FILTERING_GREATER_THAN_OPERATOR;
                    value = value.replace(COLON_OPERATOR + FILTERING_GREATER_THAN_OPERATOR, "");
                } else if (value.contains(COLON_OPERATOR + FILTERING_LESS_THAN_OR_EQUAL_TO_OPERATOR)) {
                    operator = FILTERING_LESS_THAN_OR_EQUAL_TO_OPERATOR;
                    value = value.replace(COLON_OPERATOR + FILTERING_LESS_THAN_OR_EQUAL_TO_OPERATOR, "");
                } else if (value.contains(COLON_OPERATOR + FILTERING_LESS_THAN_OPERATOR)) {
                    operator = FILTERING_LESS_THAN_OPERATOR;
                    value = value.replace(COLON_OPERATOR + FILTERING_LESS_THAN_OPERATOR, "");
                } else if (value.contains(COLON_OPERATOR)) {
                    throw new IllegalArgumentException("Invalid operator passed in parameter '" + value + "'");
                }

                FilterOptions filterOptions = new FilterOptions();
                filterOptions.setField(entry.getKey());
                filterOptions.setValue(value);
                filterOptions.setOperator(operator);
                filterOptionsList.add(filterOptions);
            }
        }
        return filterOptionsList;
    }

    private List<SortingOptions> deserializeToSortingOptions(String serialisedSortingParameters) {
        List<SortingOptions> options = new ArrayList<>();
        String[] strParts = serialisedSortingParameters.split("\\s*,\\s*");
        ArrayList<String> aList = new ArrayList<String>(Arrays.asList(strParts));
        for (String str : aList) {
            SortingOptions option = new SortingOptions();

            if (str.contains(COLON_OPERATOR + SORTING_ASCENDING_PARAMETER)) {
                option.setField(str.replace(COLON_OPERATOR + SORTING_ASCENDING_PARAMETER, ""));
                option.setOrder(SORTING_ASCENDING_PARAMETER);
            } else if (str.contains(COLON_OPERATOR + SORTING_DESCENDING_PARAMETER)) {
                option.setField(str.replace(COLON_OPERATOR + SORTING_DESCENDING_PARAMETER, ""));
                option.setOrder(SORTING_DESCENDING_PARAMETER);
            } else if (str.contains(COLON_OPERATOR)){
                throw new IllegalArgumentException("Parameter '"+ str +"' is invalid, parameter must only contain operator :asc or :desc");
            } else {
                option.setField(str);
            }
            options.add(option);
        }
        return options;
    }
}
