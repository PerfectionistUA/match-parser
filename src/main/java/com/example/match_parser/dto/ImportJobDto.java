package com.example.match_parser.dto;

import com.example.match_parser.entity.ImportStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportJobDto {

    private Long id;
    private String sourceUrl;
    private ImportStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime finishedAt;
    private String errorMessage;
    private Long matchId;
}
