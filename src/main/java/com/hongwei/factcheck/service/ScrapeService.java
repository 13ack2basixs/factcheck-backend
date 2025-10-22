package com.hongwei.factcheck.service;

import com.hongwei.factcheck.controller.ScrapeController.ScrapeResponse;
import net.dankito.readability4j.Article;
import net.dankito.readability4j.Readability4J;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ScrapeService {

    public ScrapeResponse fetch(String rawUrl) {
        try {
            String html = Jsoup.connect(rawUrl).get().outerHtml();

            Readability4J r4j = new Readability4J(rawUrl, html);
            Article text = r4j.parse();
            String title = text.getTitle();
            String content = text.getTextContent();

            return new ScrapeResponse(200, rawUrl, title, content);
        } catch (HttpStatusException e) {
            int code = e.getStatusCode();
            String message = "Fetch failed: ";
            if (code == 403) message = "Paywall restriction: ";
            return new ScrapeResponse(code, rawUrl, null, message + e.getMessage());
        } catch (IOException e) {
            return new ScrapeResponse(502, rawUrl, null, e.getMessage());
        }
    }
}
