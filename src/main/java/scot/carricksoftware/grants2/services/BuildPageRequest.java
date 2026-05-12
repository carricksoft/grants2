/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static scot.carricksoftware.grants2.constants.ApplicationIntegers.DEFAULT_PAGE;
import static scot.carricksoftware.grants2.constants.ApplicationIntegers.DEFAULT_PAGE_SIZE;

@SuppressWarnings("unused")
@Component
public class BuildPageRequest {


    @SuppressWarnings("unused")
    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, Sort sort) {
        int queryPageNumber;
        int queryPageSize;

        if (null != pageNumber)  {
            if (pageNumber <= 1) {
                queryPageNumber = 0;
            } else {
                queryPageNumber = pageNumber - 1;
            }
        } else {
            queryPageNumber = DEFAULT_PAGE.getValue();
        }

        if (null == pageSize) {
            queryPageSize = DEFAULT_PAGE_SIZE.getValue();
        } else {
            if (1000 < pageSize) {
                queryPageSize = DEFAULT_PAGE_SIZE.getValue();
            } else {
                queryPageSize = pageSize;
            }
        }
        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }
}