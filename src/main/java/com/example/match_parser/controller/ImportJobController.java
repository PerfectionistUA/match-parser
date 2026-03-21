package com.example.match_parser.controller;

import com.example.match_parser.dto.CreateImportJobRequestDto;
import com.example.match_parser.dto.ImportJobDto;
import com.example.match_parser.dto.ImportJobMapper;
import com.example.match_parser.entity.ImportJob;
import com.example.match_parser.service.ImportJobService;
import com.example.match_parser.service.ImportProcessingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/import-jobs")
@RequiredArgsConstructor
public class ImportJobController {

    private final ImportJobService importJobService;
    private final ImportProcessingService importProcessingService;

    @PostMapping
    public ImportJobDto create(@Valid @RequestBody CreateImportJobRequestDto requestDto) {
        ImportJob entity = ImportJobMapper.toEntity(requestDto);
        ImportJob saved = importJobService.create(entity);
        return ImportJobMapper.toDto(saved);
    }

    @PostMapping("/{id}/process")
    public ImportJobDto process(@PathVariable Long id) {
        return ImportJobMapper.toDto(importProcessingService.processImport(id));
    }

    @GetMapping
    public List<ImportJobDto> getAll() {
        return importJobService.getAll()
                .stream()
                .map(ImportJobMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ImportJobDto getById(@PathVariable Long id) {
        return ImportJobMapper.toDto(importJobService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        importJobService.delete(id);
    }
}