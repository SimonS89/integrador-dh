package com.example.workoutWonderland.repository;

import com.example.workoutWonderland.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.product.id =?1")
    List<Booking> findByProductId(Long productId);
    @Query("SELECT b FROM Booking b WHERE b.user.id =?1")
    List<Booking> findBookingsByUser(Long userId);
}

