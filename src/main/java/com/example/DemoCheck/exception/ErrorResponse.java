package com.example.DemoCheck.exception;

import lombok.Getter;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        String path,
        LocalDateTime timestamp
) {}