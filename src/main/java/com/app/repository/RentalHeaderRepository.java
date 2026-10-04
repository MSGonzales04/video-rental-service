package com.app.repository;

import com.app.model.RentalHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalHeaderRepository extends JpaRepository<RentalHeader,Integer> {
}
