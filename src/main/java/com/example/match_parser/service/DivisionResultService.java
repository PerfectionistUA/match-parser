package com.example.match_parser.service;

import com.example.match_parser.entity.DivisionResult;
import com.example.match_parser.entity.Match;
import com.example.match_parser.exception.ResourceNotFoundException;
import com.example.match_parser.repository.DivisionResultRepository;
import com.example.match_parser.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DivisionResultService {

    private final DivisionResultRepository divisionResultRepository;
    private final MatchRepository matchRepository;

    public DivisionResult create(Long matchId, DivisionResult divisionResult) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + matchId));

        divisionResult.setMatch(match);
        return divisionResultRepository.save(divisionResult);
    }

    public List<DivisionResult> getAll() {
        return divisionResultRepository.findAll();
    }

    public DivisionResult getById(Long id) {
        return divisionResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Division result not found with id: " + id));
    }

    public List<DivisionResult> getByMatchId(Long matchId) {
        return divisionResultRepository.findByMatchId(matchId);
    }

    public DivisionResult update(Long id, DivisionResult updatedResult) {
        DivisionResult existingResult = getById(id);

        existingResult.setDivisionName(updatedResult.getDivisionName());
        existingResult.setPlaceValue(updatedResult.getPlaceValue());
        existingResult.setCompetitorName(updatedResult.getCompetitorName());
        existingResult.setMemberNumber(updatedResult.getMemberNumber());
        existingResult.setPointsValue(updatedResult.getPointsValue());
        existingResult.setPercentValue(updatedResult.getPercentValue());
        existingResult.setTimeValue(updatedResult.getTimeValue());
        existingResult.setMatch(updatedResult.getMatch());

        return divisionResultRepository.save(existingResult);
    }

    public void delete(Long id) {
        if (!divisionResultRepository.existsById(id)) {
            throw new ResourceNotFoundException("Division result not found with id: " + id);
        }
        divisionResultRepository.deleteById(id);
    }
}
