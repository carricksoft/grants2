/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BuildPageRequestTest {
    private BuildPageRequest buildPageRequest;

    private Sort sort =  Sort.by(Sort.Order.asc("name"));

    @BeforeEach
    void setUp() {
        buildPageRequest = new BuildPageRequest();
    }

    @Test
    void buldNoPageNumberNullTest() {
        assertThat(buildPageRequest.buildPageRequest(null, 10, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void buldNoPageNumberNegativeTest() {
        assertThat(buildPageRequest.buildPageRequest(-1, 10, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void buldNoPageNumberZeroOffsetTest() {
        assertThat(buildPageRequest.buildPageRequest(10, 10, sort).getPageNumber()).isEqualTo(9);
    }


    @Test
    void buldNoPageSizeNullTest() {
        assertThat(buildPageRequest.buildPageRequest(10, null, sort).getPageSize()).isEqualTo(25);
    }

    @Test
    void buldNoPageSizeNormalTest() {
        assertThat(buildPageRequest.buildPageRequest(10, 1000, sort).getPageSize()).isEqualTo(1000);
    }

    @Test
    void buldNoPageSizeHugeTest() {
        assertThat(buildPageRequest.buildPageRequest(10, 1001, sort).getPageSize()).isEqualTo(25);
    }


}