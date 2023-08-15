package com.aces.spring.login.repository;

import com.aces.spring.login.models.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {
    // You can add custom query methods here if needed
}
