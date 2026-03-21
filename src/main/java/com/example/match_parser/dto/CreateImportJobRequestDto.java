package com.example.match_parser.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateImportJobRequestDto {

    @NotBlank
    private String sourceUrl;
}
