package com.example.workoutWonderland.repository;

import com.example.workoutWonderland.entity.Product;
import com.example.workoutWonderland.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT p FROM Product p INNER JOIN p.bookings pb WHERE pb.user.id =?1 and pb.product.id =?2")
    Optional<Product> productBookedByUser(Long userId, Long productId);
}
