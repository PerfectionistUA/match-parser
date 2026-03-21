package com.example.match_parser.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "division_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivisionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String divisionName;

    private Integer placeValue;

    @Column(nullable = false)
    private String competitorName;

    private String memberNumber;

    private Double pointsValue;

    private Double percentValue;

    private String timeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;
}
