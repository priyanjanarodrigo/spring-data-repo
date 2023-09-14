package com.myorg.sdc.util.model.dto.response.error;

import java.time.LocalDateTime;

/**
 * Record for API error response information.
 *
 * @param statusCode HTTP Status Code
 * @param path       Request Path
 * @param message    Message
 * @param timestamp  Timestamp
 */
public record ApiErrorResponse(
    int statusCode,
    String path,
    String message,
    LocalDateTime timestamp
) {

}
