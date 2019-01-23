package java_query_api_utils;

import java_query_api_utils.domain.FilterOptions;
import java_query_api_utils.domain.PaginationOptions;
import java_query_api_utils.domain.SortingOptions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java_query_api_utils.ApiQueryParameters.SORTING_ASCENDING_PARAMETER;
import static java_query_api_utils.ApiQueryParameters.SORTING_DESCENDING_PARAMETER;
import static org.junit.Assert.assertEquals;

public class ClientQueryTests {

    @Test
    public void builderWithoutPath() {
        String account = new ClientQuery.Builder().build();
        assertEquals("", account);
    }

    @Test
    public void builderWithPathDoesNotAppendQuestionMarkWhenPassed() {
        String account = new ClientQuery.Builder("user-enquiry?").build();
        assertEquals("user-enquiry?", account);
    }

    @Test
    public void builderWithPathAppendsQuestionMarkWhenNotPassed() {
        String account = new ClientQuery.Builder("user-enquiry").build();
        assertEquals("user-enquiry?", account);
    }

    @Test
    public void addSortingOptionsList() {
        List<SortingOptions> sortingOptions = new ArrayList<>();
        sortingOptions.add( new SortingOptions("firstName", SORTING_ASCENDING_PARAMETER));
        sortingOptions.add(new SortingOptions("lastName", SORTING_DESCENDING_PARAMETER));
        sortingOptions.add(new SortingOptions("organisation", SORTING_DESCENDING_PARAMETER));

        String account = new ClientQuery.Builder().addSortingOptionsList(sortingOptions).build();
        assertEquals("sort=firstName:asc,lastName:desc,organisation:desc", account);
    }

    @Test
    public void builderSortingOptions() {
        SortingOptions s1 = new SortingOptions("firstName", SORTING_ASCENDING_PARAMETER);
        SortingOptions s2 = new SortingOptions("lastName", SORTING_DESCENDING_PARAMETER);

        String account = new ClientQuery.Builder().addSortingOptions(s1)
                .addSortingOptions(s2).build();
        assertEquals("sort=firstName:asc,lastName:desc", account);
    }

    @Test
    public void addSortingParameters() {
        String account = new ClientQuery.Builder()
                .addSortingParameters("firstName", SORTING_ASCENDING_PARAMETER)
                .addSortingParameters("lastName", SORTING_DESCENDING_PARAMETER)
                .build();
        assertEquals("sort=firstName:asc,lastName:desc", account);
    }

    @Test
    public void addFilterOptionsList() {
        List<FilterOptions> filteringOptions = new ArrayList<>();
        filteringOptions.add(new FilterOptions("firstName", "Daniel", "lk"));
        filteringOptions.add(new FilterOptions("lastName", "Edmonds", "eq"));

        String account = new ClientQuery.Builder().addFilterOptionsList(filteringOptions).build();
        assertEquals("firstName=Daniel:lk&lastName=Edmonds:eq", account);
    }

    @Test
    public void addFilterOptions() {
        FilterOptions f1 = new FilterOptions("firstName", "Daniel", "lk");
        FilterOptions f2 = new FilterOptions("lastName", "Edmonds", "eq");

        String account = new ClientQuery.Builder()
                .addFilterOptions(f1)
                .addFilterOptions(f2)
                .build();
        assertEquals("firstName=Daniel:lk&lastName=Edmonds:eq", account);
    }

    @Test
    public void addFilterParameters() {
        String account = new ClientQuery.Builder()
                .addFilterParameters("firstName", "Daniel", "lk")
                .addFilterParameters("lastName", "Edmonds", "eq")
                .build();
        assertEquals("firstName=Daniel:lk&lastName=Edmonds:eq", account);
    }

    @Test
    public void addPaginationOptions() {
        PaginationOptions paginationOptions = new PaginationOptions(5, 25);

        String account = new ClientQuery.Builder().addPaginationOptions(paginationOptions).build();
        assertEquals("size=5&page=25", account);
    }

    @Test
    public void addPaginationParameters() {
        String account = new ClientQuery.Builder().addPaginationParameters(5, 25).build();
        assertEquals("size=5&page=25", account);
    }

    @Test
    public void builderBuildFullQuery() {
        List<SortingOptions> sortingOptions = new ArrayList<>();
        sortingOptions.add(new SortingOptions("firstName", SORTING_ASCENDING_PARAMETER));
        sortingOptions.add(new SortingOptions("lastName", SORTING_DESCENDING_PARAMETER));
        sortingOptions.add(new SortingOptions("organisation", SORTING_DESCENDING_PARAMETER));

        List<FilterOptions> filteringOptions = new ArrayList<>();
        filteringOptions.add(new FilterOptions("firstName", "Daniel", "lk"));
        filteringOptions.add(new FilterOptions("lastName", "Edmonds", "eq"));

        PaginationOptions paginationOptions = new PaginationOptions(5, 25);

        String account = new ClientQuery.Builder("user-enquiry")
                .addSortingOptionsList(sortingOptions)
                .addPaginationOptions(paginationOptions)
                .addFilterOptionsList(filteringOptions)
                .build();

        assertEquals("user-enquiry?sort=firstName:asc,lastName:desc,organisation:desc" +
                "&size=5&page=25&firstName=Daniel:lk&lastName=Edmonds:eq", account);
    }


}
