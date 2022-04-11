package com.tms.common.domain.model;

import com.tms.common.changeRequestDomain.enumTypes.SearchCriteria;
import lombok.Data;

@Data
public class SearchStrategy {
    private SearchCriteria searchCriteria;
    private Object value;
}
