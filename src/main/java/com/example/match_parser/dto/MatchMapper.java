package com.example.match_parser.dto;

import com.example.match_parser.entity.ImportJob;
import com.example.match_parser.entity.Match;

public class MatchMapper {

    public static MatchDto toDto(Match match) {
        if (match == null) {
            return null;
        }

        return MatchDto.builder()
                .id(match.getId())
                .externalId(match.getExternalId())
                .matchName(match.getMatchName())
                .matchDate(match.getMatchDate())
                .sourceUrl(match.getSourceUrl())
                .importJobId(match.getImportJob() != null ? match.getImportJob().getId() : null)
                .build();
    }

    public static Match toEntity(CreateMatchRequestDto dto, ImportJob importJob) {
        if (dto == null) {
            return null;
        }

        return Match.builder()
                .externalId(dto.getExternalId())
                .matchName(dto.getMatchName())
                .matchDate(dto.getMatchDate())
                .sourceUrl(dto.getSourceUrl())
                .importJob(importJob)
                .build();
    }
}
