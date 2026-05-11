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
    region_id    VARCHAR(36)  NULL,
    CONSTRAINT pk_place PRIMARY KEY (id)
);

CREATE TABLE region
(
    id           VARCHAR(36)  NOT NULL,
    version      INT          NULL,
    created_date datetime     NULL,
    updated_date datetime     NULL,
    name         VARCHAR(128) NOT NULL,
    country_id   VARCHAR(36)  NULL,
    CONSTRAINT pk_region PRIMARY KEY (id)
);

ALTER TABLE place
    ADD CONSTRAINT FK_PLACE_ON_REGION FOREIGN KEY (region_id) REFERENCES region (id);

ALTER TABLE region
    ADD CONSTRAINT FK_REGION_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);