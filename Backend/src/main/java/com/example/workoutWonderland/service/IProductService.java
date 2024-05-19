package com.example.workoutWonderland.service;

import com.example.workoutWonderland.dto.request.ProductImageRequestoDTO;
import com.example.workoutWonderland.dto.request.ProductRegisterRequestDTO;
import com.example.workoutWonderland.dto.response.ProductsByCategoriesDTO;
import com.example.workoutWonderland.entity.Product;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IProductService extends IModelService<Product> {

    Page<Product> findAllByPage(int pageNumber);

    Product create(ProductRegisterRequestDTO product) throws AlreadyExistsException, IOException, ResourceNotFoundException;

    void deleteProductImage(Long imgId) throws ResourceNotFoundException;

    void addProductImage(ProductImageRequestoDTO productImageDTO) throws ResourceNotFoundException, IOException;

    ProductsByCategoriesDTO findByCategorias(List<Long> categoriesId) throws ResourceNotFoundException;

    void addFeatureProduct(Long productoId, Long caracteristicaId) throws ResourceNotFoundException, IOException, AlreadyExistsException;

    void removeFeatureProduct(Long productoId, Long caracteristicaId) throws ResourceNotFoundException, IOException;

    List<Product> findByNameContaining(String name) throws ResourceNotFoundException;

    void changeProductCategory(Long productoId, Long categoryId) throws ResourceNotFoundException, IOException;


    List<Product> findByRangeAvailable(LocalDate startDate, LocalDate endDate) throws ResourceNotFoundException;
}