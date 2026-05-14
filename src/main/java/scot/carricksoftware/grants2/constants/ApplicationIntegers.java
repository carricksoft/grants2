/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.constants;

import lombok.Getter;

@Getter
@SuppressWarnings("ClassHasNoToStringMethod")
public enum ApplicationIntegers {

    DEFAULT_PAGE(0),
    DEFAULT_PAGE_SIZE(25);

    private final int value;
    ApplicationIntegers(int i) {
        value = i;
    }

}
