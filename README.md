# java-query-api-utils

This library provides utility classes to aid in the implementation of REST APIs that allow access to data with pagination, filtering and sorting capabilities. Utility classes provide validation, deserialization and JOOQ query creation.

## Pagination
Pagination can be implemented using both query parameters:
- **'size'** - defines the maximum number of records to return.
- **'page'** - defines which page of results to return as an offset based on page.
Both fields are validated to be an positive integer, greater than zero.
```
  e.g. /find-users?page=1&size=25
```
## Sorting
Sorting is determined through the use of the **'sort'** query string parameter.
 The value of this parameter is a comma-separated list of sort keys.
 Sort directions can optionally be appended to each sort key, separated by the ':' character:
 - **':asc'** - defines ascending order.
 - **':desc'** - defines descending order.
 When direction is not provided, the column is sorted in ascending order.
 ```
  e.g. /find-users?sort=firstName:asc,lastName:desc,organisationName
```
 This get mapped to a ORDER BY clause constructed using JOOQs .orderBy method

## Filtering
Filtering can be implemented as a query parameter named for the field to be filtered on, the value should be the value you need to filter for.
By default data will be queried based on equality, but the following optional operators an apply:
- **':eq'** - Equal (used by default)
- **':neq'** - Not equal
- **':lk'** - Like
- **':gt'** - Greater than
- **':gte'** - Greater than or equal
- **':lt'** - Less than
- **':lte'** - Less than or equal
```
  e.g. /find-users?fullName=Daniel%:lk&username=danielpedmonds:neq
```
 This gets mapped to a WHERE clause constructed  using JOOQs .where method
