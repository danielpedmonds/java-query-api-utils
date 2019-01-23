package java_query_api_utils;

import java_query_api_utils.domain.FilterOptions;
import java_query_api_utils.domain.PaginationOptions;
import java_query_api_utils.domain.SortingOptions;

import java.util.ArrayList;
import java.util.List;

import static java_query_api_utils.ApiQueryParameters.*;

/**
 * Builder class
 */
public class ClientQuery {
    private String baseUrl;
    private List<SortingOptions> sortParameters;
    private PaginationOptions paginationOptions;
    private List<FilterOptions> filterOptions;

    public static class Builder {
        private String baseUrl;
        private List<SortingOptions> sortParameters = new ArrayList<>();
        private PaginationOptions paginationOptions = new PaginationOptions();
        private List<FilterOptions> filterOptions = new ArrayList<>();

        public Builder() {
        }

        public Builder(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        /*
            Sorting
         */
        public Builder addSortingOptionsList(List<SortingOptions> sortParameters) {
            this.sortParameters = sortParameters;
            return this;
        }

        public Builder addSortingOptions(SortingOptions sortingOptions) {
            this.sortParameters.add(sortingOptions);
            return this;
        }

        public Builder addSortingParameters(String field, String order) {
            this.sortParameters.add(new SortingOptions(field, order));
            return this;
        }

        /* Pagination */

        public Builder addPaginationOptions(PaginationOptions paginationOptions) {
            this.paginationOptions = paginationOptions;
            return this;
        }

        public Builder addPaginationParameters(Integer pageSize, Integer pageNumber) {
            this.paginationOptions = new PaginationOptions(pageSize, pageNumber);
            return this;
        }

        /* Filtering */

        public Builder addFilterOptionsList(List<FilterOptions> filterOptions) {
            this.filterOptions = filterOptions;
            return this;
        }

        public Builder addFilterOptions(FilterOptions filterOptions) {
            this.filterOptions.add(filterOptions);
            return this;
        }

        public Builder addFilterParameters(String field, String value, String operator) {
            this.filterOptions.add(new FilterOptions(field, value, operator));
            return this;
        }

        public String build() {
            String query = "";
            query = buildBaseUrl(query);
            query = buildSortParameters(query);
            query = buildPaginationParameters(query);
            query = buildFilterParameters(query);
            return query;
        }

        private String buildBaseUrl(String query) {
            if (this.baseUrl != null) {
                query += baseUrl;
                if (!baseUrl.contains(QUESTION_MARK)) query += QUESTION_MARK;
            }
            return query;
        }

        private String buildSortParameters(String query) {
            if (this.sortParameters.size() > 0) {
                StringBuilder queryBuilder = new StringBuilder(addAmpersandIfNotFirstParameter(query))
                        .append(SORTING_SORT_PARAMETER)
                        .append(EQUALS);
                for (int i = 0; i < this.sortParameters.size(); i++) {
                    if (i > 0) queryBuilder.append(COMMA);
                    queryBuilder.append(this.sortParameters.get(i).getField())
                            .append(COLON_OPERATOR)
                            .append(this.sortParameters.get(i).getOrder().toString().toLowerCase());
                }
                query = queryBuilder.toString();
            }
            return query;
        }

        private String buildPaginationParameters(String query) {
            if (this.paginationOptions.getPageNumber() != null && this.paginationOptions.getPageSize() != null) {
                StringBuilder queryBuilder = new StringBuilder(addAmpersandIfNotFirstParameter(query))
                        .append(PAGINATION_SIZE_PARAMETER)
                        .append(EQUALS)
                        .append(this.paginationOptions.getPageSize())
                        .append(AMPERSAND)
                        .append(PAGINATION_PAGE_PARAMETER)
                        .append(EQUALS)
                        .append(this.paginationOptions.getPageNumber());
                query = queryBuilder.toString();
            }
            return query;
        }

        private String buildFilterParameters(String query) {
            if (this.filterOptions.size() > 0) {
                StringBuilder queryBuilder = new StringBuilder(addAmpersandIfNotFirstParameter(query));
                for (int i = 0; i < this.filterOptions.size(); i++) {
                    if (i > 0) queryBuilder.append(AMPERSAND);
                    queryBuilder.append(this.filterOptions.get(i).getField())
                            .append(EQUALS)
                            .append(this.filterOptions.get(i).getValue())
                            .append(this.filterOptions.get(i).getOperator() != null ?
                                    COLON_OPERATOR + this.filterOptions.get(i).getOperator() : "");
                }
                query = queryBuilder.toString();
            }
            return query;
        }

        private String addAmpersandIfNotFirstParameter(String query) {
            if (!query.equals("") && !query.equals(this.baseUrl) && !query.equals(this.baseUrl + QUESTION_MARK)) {
                query += AMPERSAND;
            }
            return query;
        }
    }

    private ClientQuery() {
    }
}