package com.example.workoutWonderland.controller;

import com.example.workoutWonderland.dto.request.ProductImageRequestoDTO;
import com.example.workoutWonderland.dto.request.ProductRegisterRequestDTO;
import com.example.workoutWonderland.dto.response.ProductsByCategoriesDTO;
import com.example.workoutWonderland.entity.Product;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.FeatureAlreadyAddedException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProductV2(@ModelAttribute ProductRegisterRequestDTO product) throws AlreadyExistsException, IOException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
    }

    @GetMapping()
    public ResponseEntity<Set<Product>> listProducts() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("/{id}")

    public ResponseEntity<Product> productById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findById(id));
    }

    @PutMapping("/modify")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.modify(product));
    }

    @PostMapping("/modify/features/add")
    public ResponseEntity<String> addFeature(@RequestParam Long productId, @RequestParam Long featureId) throws IOException, ResourceNotFoundException, AlreadyExistsException, FeatureAlreadyAddedException {
        productService.addFeatureProduct(productId, featureId);
        return ResponseEntity.ok("\n" + "Feature added to the product successfully.");
    }

    @PostMapping("/modify/features/remove")
    public ResponseEntity<String> removeFeatureFromProduct(@RequestParam Long productId, @RequestParam Long featureId) throws IOException, ResourceNotFoundException, FeatureAlreadyAddedException {
        productService.removeFeatureProduct(productId, featureId);
        return ResponseEntity.ok("Característica removida del producto exitosamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product deleted successfully");
    }

    @DeleteMapping("/image/{imgId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Long imgId) throws ResourceNotFoundException {
        productService.deleteProductImage(imgId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Image deleted successfully");
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<Product>> listProductsByPage(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllByPage(page));
    }

    @PostMapping("/add_image")
    public ResponseEntity<String> addProductImage(@Valid @ModelAttribute ProductImageRequestoDTO images) throws AlreadyExistsException, IOException, ResourceNotFoundException {
        productService.addProductImage(images);
        return ResponseEntity.status(HttpStatus.CREATED).body("Image/s added successfully");
    }

    @GetMapping("/by_categories")
    public ResponseEntity<ProductsByCategoriesDTO> productsByCategory(@RequestParam List<Long> categoriesIds) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByCategorias(categoriesIds));
    }

    @GetMapping("/name")
    public ResponseEntity<List<Product>> productsByName(@RequestParam String search) throws ResourceNotFoundException {
        List<Product> products = productService.findByNameContaining(search);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PatchMapping("/modify/category")
    public ResponseEntity<String> changeCategory(@RequestParam Long productId, @RequestParam Long categoryId) throws IOException, ResourceNotFoundException {
        productService.changeProductCategory(productId, categoryId);
        return ResponseEntity.ok("\n" + "Product category successfully changed.");
    }


    @GetMapping("/by_date")
    public ResponseEntity<List<Product>> findByRangeAvailable(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws ResourceNotFoundException {
        List<Product> products = productService.findByRangeAvailable(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}