package com.example.workoutWonderland.repository;

import com.example.workoutWonderland.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFavoriteRepository extends JpaRepository<Favorite,Long> {

    @Query("SELECT f FROM Favorite f WHERE f.user.id =?1")
    List<Favorite> findByUserId(Long userId);
}
