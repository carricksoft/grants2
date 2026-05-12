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

class BuildPageRequestTest {
    private BuildPageRequest buildPageRequest;

    private final Sort sort =  Sort.by(Sort.Order.asc("name"));

    @BeforeEach
    void setUp() {
        buildPageRequest = new BuildPageRequest();
    }

    @Test
    void nullPageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(null,5, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void zeroPageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(0,5, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void unityPageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(0,5, sort).getPageNumber()).isEqualTo(0);
    }

    @Test
    void largePageNumberTest() {
        assertThat(buildPageRequest.buildPageRequest(50,5, sort).getPageNumber()).isEqualTo(49);
    }









}