package com.hongwei.factcheck.controller;

import com.hongwei.factcheck.service.ScrapeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScrapeController {
    private final ScrapeService scraper;

    public ScrapeController(ScrapeService scraper) { this.scraper = scraper; }

    public record ScrapeResponse(int status, String url, String title, String content) {}

    // POST /api/scrape?url=https://example.com
    @PostMapping("/scrape")
    public ResponseEntity<ScrapeResponse> scrape(@RequestParam String url) {
        var result = scraper.fetch(url);
        return ResponseEntity.status(result.status()).body(result);
    }
}
