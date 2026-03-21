package com.example.match_parser.service;

import com.example.match_parser.entity.DivisionResult;
import com.example.match_parser.entity.ImportJob;
import com.example.match_parser.entity.ImportStatus;
import com.example.match_parser.entity.Match;
import com.example.match_parser.parser.ParsedDivisionResult;
import com.example.match_parser.parser.ParsedMatchData;
import com.example.match_parser.parser.PractiScoreClient;
import com.example.match_parser.parser.PractiScoreParser;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ImportProcessingService {

    private final ImportJobService importJobService;
    private final MatchService matchService;
    private final DivisionResultService divisionResultService;
    private final PractiScoreClient practiScoreClient;
    private final PractiScoreParser practiScoreParser;
    private final SnapshotService snapshotService;

    public ImportJob processImport(Long importJobId) {
        ImportJob importJob = importJobService.getById(importJobId);

        if (importJob.getMatch() != null) {
            return importJob;
        }

        try {
            Document document = practiScoreClient.fetchDocument(importJob.getSourceUrl());

            String html = document.outerHtml();
            String snapshotPath = snapshotService.saveHtml(importJob.getId(), html);

            ParsedMatchData parsedData = practiScoreParser.parse(importJob.getSourceUrl(), document);

            Match match = Match.builder()
                    .externalId(parsedData.getExternalId())
                    .matchName(parsedData.getMatchName())
                    .matchDate(parsedData.getMatchDate())
                    .sourceUrl(parsedData.getSourceUrl())
                    .importJob(importJob)
                    .build();

            Match savedMatch = matchService.create(match);

            for (ParsedDivisionResult parsedResult : parsedData.getResults()) {
                DivisionResult result = DivisionResult.builder()
                        .divisionName(parsedResult.getDivisionName())
                        .placeValue(parsedResult.getPlaceValue())
                        .competitorName(parsedResult.getCompetitorName())
                        .memberNumber(parsedResult.getMemberNumber())
                        .pointsValue(parsedResult.getPointsValue())
                        .percentValue(parsedResult.getPercentValue())
                        .timeValue(parsedResult.getTimeValue())
                        .match(savedMatch)
                        .build();

                divisionResultService.create(savedMatch.getId(), result);
            }

            importJob.setStatus(ImportStatus.SUCCESS);
            importJob.setFinishedAt(LocalDateTime.now());
            importJob.setErrorMessage(null);

            return importJobService.create(importJob);

        } catch (Exception ex) {
            importJob.setStatus(ImportStatus.FAILED);
            importJob.setFinishedAt(LocalDateTime.now());
            importJob.setErrorMessage(ex.getMessage());

            return importJobService.create(importJob);
        }
    }
}