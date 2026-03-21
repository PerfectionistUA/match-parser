package com.example.match_parser.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMatchRequestDto {

    private String externalId;

    @NotBlank
    private String matchName;

    private LocalDate matchDate;

    @NotBlank
    private String sourceUrl;

    private Long importJobId;
}
