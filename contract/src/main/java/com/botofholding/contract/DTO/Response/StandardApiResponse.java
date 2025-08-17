package com.botofholding.contract.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandardApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}