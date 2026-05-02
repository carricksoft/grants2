/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

CREATE TABLE place
(
    id           VARCHAR(36)  NOT NULL,
    version      INT          NULL,
    created_date datetime     NULL,
    updated_date datetime     NULL,
    name         VARCHAR(128) NOT NULL,
    CONSTRAINT pk_place PRIMARY KEY (id)
);