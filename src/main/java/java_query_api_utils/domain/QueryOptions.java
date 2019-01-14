package java_query_api_utils.domain;

import java.util.ArrayList;
import java.util.List;

public class QueryOptions {

    private PaginationOptions paginationOptions = new PaginationOptions();
    private List<FilterOptions> filterOptions = new ArrayList<>();
    private List<SortingOptions> SortingOptions = new ArrayList<>();

    public PaginationOptions getPaginationOptions() {
        return paginationOptions;
    }

    public void setPaginationOptions(PaginationOptions paginationOptions) {
        this.paginationOptions = paginationOptions;
    }

    public List<FilterOptions> getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(List<FilterOptions> filterOptions) {
        this.filterOptions = filterOptions;
    }

    public List<SortingOptions> getSortingOptions() {
        return SortingOptions;
    }

    public void setSortingOptions(List<SortingOptions> SortingOption) {
        this.SortingOptions = SortingOption;
    }
}
