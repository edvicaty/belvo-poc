package com.belvopoc.belvopoc.belvo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/belvo")
public class BelvoController {

    @GetMapping
    public ResponseEntity<String> testAuth() {
        return ResponseEntity.ok("Mock secured endpoint response");
    }
}
