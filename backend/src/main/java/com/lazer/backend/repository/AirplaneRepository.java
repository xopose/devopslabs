package com.lazer.backend.repository;

import com.lazer.backend.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    @Transactional
    @Modifying
    @Query("update Airplane a set a.in_flight = ?1 where a.id = ?2")
    void updateAirplaneById(Boolean in_flight, Long id);
}
