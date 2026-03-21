package com.example.match_parser.service;

import com.example.match_parser.entity.Match;
import com.example.match_parser.exception.ResourceNotFoundException;
import com.example.match_parser.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public Match create(Match match) {
        return matchRepository.save(match);
    }

    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    public Match getById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));
    }

    public Match update(Long id, Match updatedMatch) {
        Match existingMatch = getById(id);

        existingMatch.setExternalId(updatedMatch.getExternalId());
        existingMatch.setMatchName(updatedMatch.getMatchName());
        existingMatch.setMatchDate(updatedMatch.getMatchDate());
        existingMatch.setSourceUrl(updatedMatch.getSourceUrl());
        existingMatch.setImportJob(updatedMatch.getImportJob());

        return matchRepository.save(existingMatch);
    }

    public void delete(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Match not found with id: " + id);
        }
        matchRepository.deleteById(id);
    }
}
