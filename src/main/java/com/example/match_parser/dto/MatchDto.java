package com.example.match_parser.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDto {

    private Long id;
    private String externalId;
    private String matchName;
    private LocalDate matchDate;
    private String sourceUrl;
    private Long importJobId;
}
