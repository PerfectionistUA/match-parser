package com.example.match_parser.parser;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParsedMatchData {

    private String externalId;
    private String matchName;
    private LocalDate matchDate;
    private String sourceUrl;

    @Builder.Default
    private List<ParsedDivisionResult> results = new ArrayList<>();
}