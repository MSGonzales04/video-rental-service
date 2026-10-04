package com.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental_headers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rentalId;

    @Column(name = "date_rented", nullable = false)
    private LocalDate dateRented;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Builder.Default
    @OneToMany(mappedBy = "rentalHeader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalDetail> rentalDetails = new ArrayList<>();

    @PrePersist
    protected void onCreate() { this.createdDate = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { this.modifiedDate = LocalDateTime.now(); }
}