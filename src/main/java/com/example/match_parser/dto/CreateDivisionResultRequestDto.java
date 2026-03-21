package com.example.match_parser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDivisionResultRequestDto {

    @NotBlank
    private String divisionName;

    private Integer placeValue;

    @NotBlank
    private String competitorName;

    private String memberNumber;
    private Double pointsValue;
    private Double percentValue;
    private String timeValue;

    @NotNull
    private Long matchId;
}
