package com.example.match_parser.parser;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParsedDivisionResult {

    private String divisionName;
    private Integer placeValue;
    private String competitorName;
    private String memberNumber;
    private Double pointsValue;
    private Double percentValue;
    private String timeValue;
}
