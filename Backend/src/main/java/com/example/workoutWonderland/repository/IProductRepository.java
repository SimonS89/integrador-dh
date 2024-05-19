package com.example.workoutWonderland.repository;

import com.example.workoutWonderland.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.category.id IN :categories")
    List<Product> findByCategoriasIn(@Param("categories") List<Long> categorias);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:productName% ORDER BY p.name")
    List<Product> findByNameContaining(@Param("productName") String productName);

    @Query("SELECT p FROM Product p LEFT JOIN p.bookings b WHERE (?1 < b.startDate OR ?1 > b.endDate) AND (?2 < b.startDate OR ?2 > b.endDate) OR b.id IS NULL")
    List<Product> findByRangeAvailable(LocalDate startDate, LocalDate endDate);
}

