package com.example.retailapp.web;

import java.time.OffsetDateTime;

public record ApiError(String path, int status, String error, String message, OffsetDateTime timestamp) {}
