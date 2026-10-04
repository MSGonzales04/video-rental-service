package com.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "movie_name", nullable = false, length = 100)
    private String movieName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Column(name = "date_added", nullable = false)
    private LocalDate dateAdded;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "number_in_stock", nullable = false)
    private Integer numberInStock;

    @Column(name = "number_available", nullable = false)
    private Integer numberAvailable;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() { this.createdDate = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { this.modifiedDate = LocalDateTime.now(); }
}
