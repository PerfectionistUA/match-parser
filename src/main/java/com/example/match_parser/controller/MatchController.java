package com.example.match_parser.controller;

import com.example.match_parser.dto.CreateMatchRequestDto;
import com.example.match_parser.dto.MatchDto;
import com.example.match_parser.dto.MatchMapper;
import com.example.match_parser.entity.ImportJob;
import com.example.match_parser.entity.Match;
import com.example.match_parser.service.ImportJobService;
import com.example.match_parser.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final ImportJobService importJobService;

    @PostMapping
    public MatchDto create(@Valid @RequestBody CreateMatchRequestDto requestDto) {
        ImportJob importJob = null;
        if (requestDto.getImportJobId() != null) {
            importJob = importJobService.getById(requestDto.getImportJobId());
        }

        Match match = MatchMapper.toEntity(requestDto, importJob);
        Match saved = matchService.create(match);
        return MatchMapper.toDto(saved);
    }

    @GetMapping
    public List<MatchDto> getAll() {
        return matchService.getAll()
                .stream()
                .map(MatchMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public MatchDto getById(@PathVariable Long id) {
        return MatchMapper.toDto(matchService.getById(id));
    }

    @PutMapping("/{id}")
    public MatchDto update(@PathVariable Long id, @Valid @RequestBody CreateMatchRequestDto requestDto) {
        ImportJob importJob = null;
        if (requestDto.getImportJobId() != null) {
            importJob = importJobService.getById(requestDto.getImportJobId());
        }

        Match updatedMatch = MatchMapper.toEntity(requestDto, importJob);
        Match saved = matchService.update(id, updatedMatch);
        return MatchMapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        matchService.delete(id);
    }
}
