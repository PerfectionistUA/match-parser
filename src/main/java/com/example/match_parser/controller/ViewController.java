package com.example.match_parser.controller;

import com.example.match_parser.dto.CreateImportJobRequestDto;
import com.example.match_parser.dto.ImportFormDto;
import com.example.match_parser.dto.ImportJobMapper;
import com.example.match_parser.entity.ImportJob;
import com.example.match_parser.entity.Match;
import com.example.match_parser.service.DivisionResultService;
import com.example.match_parser.service.ImportJobService;
import com.example.match_parser.service.ImportProcessingService;
import com.example.match_parser.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ImportJobService importJobService;
    private final ImportProcessingService importProcessingService;
    private final MatchService matchService;
    private final DivisionResultService divisionResultService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("importForm", new ImportFormDto());
        return "index";
    }

    @PostMapping("/import")
    public String importMatch(
            @ModelAttribute @Valid ImportFormDto importForm,
            org.springframework.validation.BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        CreateImportJobRequestDto requestDto = CreateImportJobRequestDto.builder()
                .sourceUrl(importForm.getSourceUrl())
                .build();

        ImportJob job = importJobService.create(ImportJobMapper.toEntity(requestDto));
        importProcessingService.processImport(job.getId());

        return "redirect:/imports";
    }

    @GetMapping("/imports")
    public String imports(Model model) {
        model.addAttribute("imports", importJobService.getAll());
        return "imports";
    }

    @GetMapping("/matches")
    public String matches(Model model) {
        model.addAttribute("matches", matchService.getAll());
        return "matches";
    }

    @GetMapping("/matches/{id}")
    public String matchDetails(@PathVariable Long id, Model model) {
        Match match = matchService.getById(id);
        model.addAttribute("match", match);
        model.addAttribute("results", divisionResultService.getByMatchId(id));
        return "match-details";
    }
}
