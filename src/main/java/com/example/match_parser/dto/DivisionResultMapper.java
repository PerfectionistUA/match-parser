package com.example.match_parser.dto;

import com.example.match_parser.entity.DivisionResult;
import com.example.match_parser.entity.Match;

public class DivisionResultMapper {

    public static DivisionResultDto toDto(DivisionResult entity) {
        if (entity == null) {
            return null;
        }

        return DivisionResultDto.builder()
                .id(entity.getId())
                .divisionName(entity.getDivisionName())
                .placeValue(entity.getPlaceValue())
                .competitorName(entity.getCompetitorName())
                .memberNumber(entity.getMemberNumber())
                .pointsValue(entity.getPointsValue())
                .percentValue(entity.getPercentValue())
                .timeValue(entity.getTimeValue())
                .matchId(entity.getMatch() != null ? entity.getMatch().getId() : null)
                .build();
    }

    public static DivisionResult toEntity(CreateDivisionResultRequestDto dto, Match match) {
        if (dto == null) {
            return null;
        }

        return DivisionResult.builder()
                .divisionName(dto.getDivisionName())
                .placeValue(dto.getPlaceValue())
                .competitorName(dto.getCompetitorName())
                .memberNumber(dto.getMemberNumber())
                .pointsValue(dto.getPointsValue())
                .percentValue(dto.getPercentValue())
                .timeValue(dto.getTimeValue())
                .match(match)
                .build();
    }
}
