package com.barbook.booking.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ApiResponse<T> {

    private boolean success;
    private T data;

}
