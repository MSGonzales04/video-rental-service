package com.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "rental_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_detail_id")
    private Integer rentalDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    private RentalHeader rentalHeader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "date_returned")
    private LocalDate dateReturned;
}