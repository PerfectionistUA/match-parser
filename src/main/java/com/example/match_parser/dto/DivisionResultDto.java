package com.example.match_parser.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivisionResultDto {

    private Long id;
    private String divisionName;
    private Integer placeValue;
    private String competitorName;
    private String memberNumber;
    private Double pointsValue;
    private Double percentValue;
    private String timeValue;
    private Long matchId;
}
