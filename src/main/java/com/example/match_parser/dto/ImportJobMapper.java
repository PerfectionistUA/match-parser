package com.example.match_parser.dto;

import com.example.match_parser.entity.ImportJob;
import com.example.match_parser.entity.ImportStatus;
import com.example.match_parser.entity.Match;

import java.time.LocalDateTime;

public class ImportJobMapper {

    public static ImportJobDto toDto(ImportJob entity) {
        if (entity == null) {
            return null;
        }

        return ImportJobDto.builder()
                .id(entity.getId())
                .sourceUrl(entity.getSourceUrl())
                .status(entity.getStatus())
                .requestedAt(entity.getRequestedAt())
                .finishedAt(entity.getFinishedAt())
                .errorMessage(entity.getErrorMessage())
                .matchId(entity.getMatch() != null ? entity.getMatch().getId() : null)
                .build();
    }

    public static ImportJob toEntity(CreateImportJobRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return ImportJob.builder()
                .sourceUrl(dto.getSourceUrl())
                .status(ImportStatus.NEW)
                .requestedAt(LocalDateTime.now())
                .finishedAt(null)
                .errorMessage(null)
                .build();
    }
}
