package com.example.match_parser.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportFormDto {

    @NotBlank
    private String sourceUrl;
}
