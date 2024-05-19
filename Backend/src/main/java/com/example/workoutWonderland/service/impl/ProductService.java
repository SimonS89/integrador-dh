package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.dto.request.ProductImageRequestoDTO;
import com.example.workoutWonderland.dto.request.ProductRegisterRequestDTO;
import com.example.workoutWonderland.dto.response.ProductsByCategoriesDTO;
import com.example.workoutWonderland.entity.*;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.IProductRepository;
import com.example.workoutWonderland.service.IFeatureService;
import com.example.workoutWonderland.service.IImageService;
import com.example.workoutWonderland.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final CategoryService categoryService;
    private final IImageService imageService;
    private final IFeatureService featureService;
    private final ObjectMapper objectMapper;

    public ProductService(IProductRepository productRepository, CategoryService categoryService, IImageService imageService, IFeatureService featureService, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.imageService = imageService;
        this.featureService = featureService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Product create(Product product) throws AlreadyExistsException {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) throws ResourceNotFoundException {
        return getProduct(id);
    }

    @Override
    public Product modify(Product product) throws ResourceNotFoundException {
        getProduct(product.getId());
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Product productSearch = getProduct(id);
        if (productSearch.getImages() != null) {
            List<String> imgNames = productSearch.getImages().stream().map(Image::getName).toList();
            imgNames.forEach(imageService::deleteFromAws);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Set<Product> findAll() throws ResourceNotFoundException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) throw new ResourceNotFoundException("There are no products stored");
        return new HashSet<>(products);
    }

    @Override
    public Page<Product> findAllByPage(int pageNumber) {
        return productRepository.findAll(PageRequest.of(pageNumber, 10));
    }

    @Override
    public Product create(ProductRegisterRequestDTO product) throws AlreadyExistsException, IOException, ResourceNotFoundException {
        Product productCreate = new Product();
        productCreate.setName(product.getName());
        productCreate.setDescription(product.getDescription());
        productCreate.setCategory(categoryService.findById(product.getCategoryId()));
        productCreate.setPrice(product.getPrice());
        productCreate.setStock(product.getStock());
        productCreate.setGenre(product.getGenre());
        Policy policy = new Policy();
        if (product.getCategoryId() > 4) policy.setId(5l);
        else policy.setId(product.getCategoryId());
        productCreate.setPolicy(policy);
        productCreate.setFeatures(new ArrayList<>());
        for (Long featureId : product.getFeaturesIds())
            productCreate.getFeatures().add(featureService.findById(featureId));
        Product prod = productRepository.save(productCreate);
//        prod.setImages(new ArrayList<>());
//        if (product.getImages() != null) {
//            for (MultipartFile img : product.getImages()) {
//                productCreate.getImages().add(imageService.createImage(img, prod));
//            }
//        }
        return prod;
    }

    @Override
    public void deleteProductImage(Long imgId) throws ResourceNotFoundException {
        imageService.delete(imgId);
    }

    @Override
    public void addProductImage(ProductImageRequestoDTO productImageDTO) throws ResourceNotFoundException, IOException {
        Product productSearch = getProduct(productImageDTO.getProductId());
        for (MultipartFile img : productImageDTO.getImages()) {
            imageService.createImage(img, productSearch);
        }
    }

    @Override
    public ProductsByCategoriesDTO findByCategorias(List<Long> categoriesId) throws ResourceNotFoundException {
        List<Product> products = productRepository.findByCategoriasIn(categoriesId);
        if (products.isEmpty()) throw new ResourceNotFoundException("There are no products stored");
        Long totalProducts = productRepository.count();
        ProductsByCategoriesDTO response = new ProductsByCategoriesDTO(totalProducts, products.size(), products);
        return response;
    }

    @Override
    public void addFeatureProduct(Long productoId, Long featureId) throws ResourceNotFoundException, IOException, AlreadyExistsException {
        Product productSearch = getProduct(productoId);
        Feature feature = featureService.findById(featureId);
        if (!productSearch.getFeatures().contains(feature)) {
            productSearch.getFeatures().add(feature);
            productRepository.save(productSearch);
        } else {
            throw new AlreadyExistsException("Feature already added to the product.");
        }
    }


    @Override
    public void removeFeatureProduct(Long productoId, Long caracteristicaId) throws ResourceNotFoundException, IOException {
        Product productSearch = getProduct(productoId);
        Feature feature = featureService.findById(caracteristicaId);
        productSearch.getFeatures().remove(feature);
        productRepository.save(productSearch);
    }


    private Product getProduct(Long id) throws ResourceNotFoundException {
        Optional<Product> productSearch = productRepository.findById(id);
        if (productSearch.isEmpty()) throw new ResourceNotFoundException("Product not found");
        return productSearch.get();
    }

    @Override
    public List<Product> findByNameContaining(String name) throws ResourceNotFoundException {
        List<Product> products = productRepository.findByNameContaining(name);
        if (products.isEmpty()) throw new ResourceNotFoundException("Product not found");
        return products;
    }

    @Override
    public void changeProductCategory(Long productId, Long categoryId) throws ResourceNotFoundException, IOException {
        Product product = getProduct(productId);

        Optional<Category> categoryOptional = Optional.ofNullable(categoryService.findById(categoryId));

        if (categoryOptional.isPresent()) {
            Category newCategory = categoryOptional.get();
            product.setCategory(newCategory);
            Policy newPolicy = new Policy();
            if (categoryId > 4) newPolicy.setId(5l);
            else newPolicy.setId(categoryId);
            product.setPolicy(newPolicy);
            productRepository.save(product);
        } else {
            throw new ResourceNotFoundException("Category not found");
        }
    }

    @Override
    public List<Product> findByRangeAvailable(LocalDate startDate, LocalDate endDate) throws ResourceNotFoundException {
        List<Product> filteredProducts = productRepository.findByRangeAvailable(startDate, endDate);
        if (filteredProducts.isEmpty()) throw new ResourceNotFoundException("Product not found");
        return filteredProducts;
    }


}
