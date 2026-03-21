package com.example.match_parser.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "import_jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String sourceUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImportStatus status;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime finishedAt;

    @Column(length = 2000)
    private String errorMessage;

    @OneToOne(mappedBy = "importJob")
    private Match match;
}
