package com.example.match_parser.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PractiScoreParser {

    public ParsedMatchData parse(String sourceUrl, Document document) {
        String externalId = extractUuidFromUrl(sourceUrl);
        String matchName = extractMatchName(document);
        LocalDate matchDate = extractMatchDate(document);
        String divisionName = extractDivisionName(document);

        ParsedMatchData data = ParsedMatchData.builder()
                .externalId(externalId)
                .matchName(matchName)
                .matchDate(matchDate)
                .sourceUrl(sourceUrl)
                .build();

        data.getResults().addAll(extractResults(document, divisionName));

        return data;
    }

    private String extractUuidFromUrl(String url) {
        int lastSlash = url.lastIndexOf('/');
        if (lastSlash >= 0 && lastSlash < url.length() - 1) {
            return url.substring(lastSlash + 1);
        }
        return UUID.randomUUID().toString();
    }

    private String extractMatchName(Document document) {
        Element matchNameEl = document.selectFirst(".match-name");
        if (matchNameEl != null && !matchNameEl.text().isBlank()) {
            return matchNameEl.text().trim();
        }

        Element h3 = document.selectFirst("h3");
        if (h3 != null && !h3.text().isBlank()) {
            String text = h3.text().trim();
            int sep = text.lastIndexOf(" - ");
            if (sep > 0) {
                return text.substring(0, sep).trim();
            }
            return text;
        }

        Element title = document.selectFirst("title");
        if (title != null && !title.text().isBlank()) {
            String text = title.text().trim();

            if (text.contains(" : ")) {
                return text.substring(0, text.indexOf(" : ")).trim();
            }

            if (text.contains("|")) {
                return text.substring(0, text.indexOf("|")).trim();
            }

            return text;
        }

        return "Unknown Match";
    }

    private LocalDate extractMatchDate(Document document) {
        Element matchDateEl = document.selectFirst(".match-date");
        if (matchDateEl != null && !matchDateEl.text().isBlank()) {
            return parseDateSafe(matchDateEl.text().trim());
        }

        Element h3 = document.selectFirst("h3");
        if (h3 != null && !h3.text().isBlank()) {
            String text = h3.text().trim();
            int sep = text.lastIndexOf(" - ");
            if (sep > 0) {
                return parseDateSafe(text.substring(sep + 3).trim());
            }
        }

        Element title = document.selectFirst("title");
        if (title != null && !title.text().isBlank()) {
            String text = title.text().trim();
            int sep = text.lastIndexOf(" : ");
            if (sep > 0) {
                return parseDateSafe(text.substring(sep + 3).trim());
            }
        }

        return null;
    }

    private String extractDivisionName(Document document) {
        Element divisionNameEl = document.selectFirst(".division-name");
        if (divisionNameEl != null && !divisionNameEl.text().isBlank()) {
            return divisionNameEl.text().trim();
        }

        Element divisionHead = document.selectFirst(".division_head");
        if (divisionHead != null && !divisionHead.text().isBlank()) {
            String text = divisionHead.text().trim();
            if (text.startsWith("Match Results - ")) {
                return text.substring("Match Results - ".length()).trim();
            }
            return text;
        }

        return "Unknown Division";
    }

    private List<ParsedDivisionResult> extractResults(Document document, String divisionName) {
        List<ParsedDivisionResult> results = new ArrayList<>();

        Elements rows = document.select("table tbody tr");
        if (rows.isEmpty()) {
            rows = document.select("table tr");
        }

        for (Element row : rows) {
            Elements cols = row.select("td");

            if (cols.size() < 4) {
                continue;
            }

            Integer placeValue = parseIntegerSafe(cols.get(0).text());
            if (placeValue == null) {
                continue;
            }

            String competitorName = cols.get(1).text().trim();
            String memberNumber = cols.size() > 2 ? emptyToNull(cols.get(2).text()) : null;

            Double pointsValue = null;
            Double percentValue = null;

            if (cols.size() >= 9) {
                pointsValue = parseDoubleSafe(cols.get(7).text());
                percentValue = parsePercentSafe(cols.get(8).text());
            } else if (cols.size() >= 4) {
                pointsValue = parseDoubleSafe(cols.get(2).text());
                percentValue = parsePercentSafe(cols.get(3).text());
            }

            if (competitorName.isBlank()) {
                continue;
            }

            ParsedDivisionResult result = ParsedDivisionResult.builder()
                    .divisionName(divisionName)
                    .placeValue(placeValue)
                    .competitorName(competitorName)
                    .memberNumber(memberNumber)
                    .pointsValue(pointsValue)
                    .percentValue(percentValue)
                    .timeValue(null)
                    .build();

            results.add(result);
        }

        return results;
    }

    private LocalDate parseDateSafe(String value) {
        try {
            return LocalDate.parse(value);
        } catch (Exception ex) {
            return null;
        }
    }

    private Integer parseIntegerSafe(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception ex) {
            return null;
        }
    }

    private Double parseDoubleSafe(String value) {
        try {
            String normalized = value
                    .replace(",", ".")
                    .replace("\u00A0", " ")
                    .trim();
            return Double.parseDouble(normalized);
        } catch (Exception ex) {
            return null;
        }
    }

    private Double parsePercentSafe(String value) {
        try {
            String normalized = value
                    .replace("%", "")
                    .replace("\u00A0", " ")
                    .trim()
                    .replace(",", ".");
            return Double.parseDouble(normalized);
        } catch (Exception ex) {
            return null;
        }
    }

    private String emptyToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}