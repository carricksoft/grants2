/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

ALTER TABLE region
    ADD country_id VARCHAR(36) NULL;

ALTER TABLE place
    ADD region_id VARCHAR(36) NULL;

ALTER TABLE place
    ADD CONSTRAINT FK_PLACE_ON_REGION FOREIGN KEY (region_id) REFERENCES region (id);

ALTER TABLE region
    ADD CONSTRAINT FK_REGION_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);