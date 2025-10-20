package com.hongwei.factcheck.service;

import com.hongwei.factcheck.controller.ScrapeController.ScrapeResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ScrapeService {

    public ScrapeResponse fetch(String rawUrl) {
        try {
            Document doc = Jsoup.connect(rawUrl)
                .userAgent("Mozilla/5.0 (compatible; FactCheckBot/1.0)")
                .referrer("https://google.com")
                .timeout(10_000)
                .followRedirects(true)
                .maxBodySize(0)
                .get();

            String title = doc.title();
            String text = doc.body() != null ? doc.body().text() : "";

            return new ScrapeResponse(200, rawUrl, title, text);
        } catch (Exception e) {
            return new ScrapeResponse(400, rawUrl, null, "Fetch failed: " + e.getMessage());
        }
    }
}
