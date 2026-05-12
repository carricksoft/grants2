/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.constants;

import org.springframework.stereotype.Component;

@SuppressWarnings("UtilityClass")
@Component
public final class ApplicationConstants {
    private ApplicationConstants() {
        super();
        // to stop checkstyle complaining
    }

    public static final int NAME_FIELD_LENGTH = 128;

}
