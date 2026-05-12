/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import scot.carricksoftware.grants2.exceptions.NotFoundException;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scot.carricksoftware.grants2.constants.ApplicationIntegers.DEFAULT_PAGE_SIZE;

class BuildPageRequestTest {
    private BuildPageRequest buildPageRequest;

    private final Sort sort = Sort.by(Sort.Order.asc("name"));

    @BeforeEach
    void setUp() {
        buildPageRequest = new BuildPageRequest();
    }

    @Test
    void nullPageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(null, 5, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void negativePageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(-2, 5, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void zeroPageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(0, 5, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void unityPageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(0, 5, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void largePageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(50, 5, sort).getPageNumber()).isEqualTo(49);
    }

    @Test
    void nullPageSizeTest() {
        assertThat(buildPageRequest.buildPageRequest(10, null, sort).getPageSize())
                .isEqualTo(DEFAULT_PAGE_SIZE.getValue());
    }

    @Test
    void maximumPageSizeTest() {
        assertThat(buildPageRequest.buildPageRequest(10, 999, sort).getPageSize())
                .isEqualTo(999);
    }

    @Test
    void topPageSizeTest() {
        assertThat(buildPageRequest.buildPageRequest(10, 1000, sort).getPageSize())
                .isEqualTo(1000);
    }

    @Test
    void tooLargeSizeTest() {
        assertThat(buildPageRequest.buildPageRequest(10, 1001, sort).getPageSize())
                .isEqualTo(DEFAULT_PAGE_SIZE.getValue());
    }
}