package com.hyu.electronicsecwebsitebe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class RatellimitController {

    @GetMapping("/api/test/rate-limit")
    public ResponseEntity<?> testRateLimit() {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Request accepted",
                        "time", LocalDateTime.now().toString()
                )
        );
    }
}
