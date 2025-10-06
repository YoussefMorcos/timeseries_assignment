package com.example.backend;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// mapping to the database schema

@Entity
@Table(name = "time_series")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime ts;
    private String source;
    private String measure;
    private Double floatValue;
}
