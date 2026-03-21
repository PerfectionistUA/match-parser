package com.example.match_parser.service;

import com.example.match_parser.entity.ImportJob;
import com.example.match_parser.exception.ResourceNotFoundException;
import com.example.match_parser.repository.ImportJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportJobService {

    private final ImportJobRepository importJobRepository;

    public ImportJob create(ImportJob importJob) {
        return importJobRepository.save(importJob);
    }

    public List<ImportJob> getAll() {
        return importJobRepository.findAll();
    }

    public ImportJob getById(Long id) {
        return importJobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Import job not found with id: " + id));
    }

    public void delete(Long id) {
        if (!importJobRepository.existsById(id)) {
            throw new ResourceNotFoundException("Import job not found with id: " + id);
        }
        importJobRepository.deleteById(id);
    }
}
