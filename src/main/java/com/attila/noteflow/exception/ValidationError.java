package com.attila.noteflow.exception;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ValidationError(Instant timestamp, String errorCode, Map<String, List<String>> fieldErrors, String path) {
}
