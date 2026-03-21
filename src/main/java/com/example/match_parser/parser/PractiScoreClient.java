package com.example.match_parser.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class PractiScoreClient {

    public Document fetchDocument(String url) throws IOException {

        if (url.equals("demo://sample")) {
            return loadDocumentFromResource("mock-pages/sample-match.html");
        }

        if (url.equals("demo://realistic")) {
            return loadDocumentFromResource("mock-pages/sample-match-realistic.html");
        }

        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();
    }

    private Document loadDocumentFromResource(String resourcePath) throws IOException {
        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(resourcePath);

        if (is == null) {
            throw new RuntimeException("Demo HTML not found: " + resourcePath);
        }

        return Jsoup.parse(is, "UTF-8", "");
    }
}