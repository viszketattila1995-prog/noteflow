package com.attila.noteflow.exception;

import java.time.Instant;

public record ApiError(Instant timestamp, String errorCode, String message, String path) {
}
