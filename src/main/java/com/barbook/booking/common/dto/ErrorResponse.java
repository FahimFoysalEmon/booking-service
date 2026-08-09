package com.barbook.booking.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int code;        // HTTP status, e.g. 401
    private String message;  // human-readable message
    private String path;     // optional but useful
}