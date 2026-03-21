package com.example.match_parser.controller;

import com.example.match_parser.dto.CreateDivisionResultRequestDto;
import com.example.match_parser.dto.DivisionResultDto;
import com.example.match_parser.dto.DivisionResultMapper;
import com.example.match_parser.entity.DivisionResult;
import com.example.match_parser.entity.Match;
import com.example.match_parser.service.DivisionResultService;
import com.example.match_parser.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class DivisionResultController {

    private final DivisionResultService divisionResultService;
    private final MatchService matchService;

    @PostMapping
    public DivisionResultDto create(@Valid @RequestBody CreateDivisionResultRequestDto requestDto) {
        Match match = matchService.getById(requestDto.getMatchId());
        DivisionResult entity = DivisionResultMapper.toEntity(requestDto, match);
        DivisionResult saved = divisionResultService.create(requestDto.getMatchId(), entity);
        return DivisionResultMapper.toDto(saved);
    }

    @GetMapping
    public List<DivisionResultDto> getAll() {
        return divisionResultService.getAll()
                .stream()
                .map(DivisionResultMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public DivisionResultDto getById(@PathVariable Long id) {
        return DivisionResultMapper.toDto(divisionResultService.getById(id));
    }

    @GetMapping("/match/{matchId}")
    public List<DivisionResultDto> getByMatchId(@PathVariable Long matchId) {
        return divisionResultService.getByMatchId(matchId)
                .stream()
                .map(DivisionResultMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public DivisionResultDto update(
            @PathVariable Long id,
            @Valid @RequestBody CreateDivisionResultRequestDto requestDto
    ) {
        Match match = matchService.getById(requestDto.getMatchId());
        DivisionResult entity = DivisionResultMapper.toEntity(requestDto, match);
        DivisionResult saved = divisionResultService.update(id, entity);
        return DivisionResultMapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        divisionResultService.delete(id);
    }
}
