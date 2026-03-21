package com.example.match_parser.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;

    @Column(nullable = false)
    private String matchName;

    private LocalDate matchDate;

    @Column(nullable = false, length = 1000)
    private String sourceUrl;

    @OneToOne
    @JoinColumn(name = "import_job_id")
    private ImportJob importJob;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DivisionResult> divisionResults = new ArrayList<>();
}
