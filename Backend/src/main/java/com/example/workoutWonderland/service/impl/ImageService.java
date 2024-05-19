package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.entity.Image;
import com.example.workoutWonderland.entity.Product;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.IImageRepository;
import com.example.workoutWonderland.service.IImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImageService implements IImageService {
    private final IImageRepository imageRepository;

    private final StorageService storageService;

    public ImageService(IImageRepository imageRepository, StorageService storageService) {
        this.imageRepository = imageRepository;
        this.storageService = storageService;
    }

    @Override
    public Image createImage(MultipartFile img, Product product) throws IOException {
        Image image = storageService.uploadFile(img);
        image.setProduct(product);
        return imageRepository.save(image);
    }

    @Override
    public Image create(Image image) throws AlreadyExistsException {
        return imageRepository.save(image);
    }

    @Override
    public Image findById(Long id) throws ResourceNotFoundException {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isEmpty()) throw new ResourceNotFoundException("Image not found");
        return image.get();
    }

    @Override
    public Image modify(Image image) throws ResourceNotFoundException {
        return imageRepository.save(image);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Image> imageSearch = imageRepository.findById(id);
        if (imageSearch.isEmpty()) throw new ResourceNotFoundException("Image not found");
        storageService.deleteFile(imageSearch.get().getName());
        imageRepository.deleteById(id);
    }

    @Override
    public void deleteFromAws(String imgName) {
        storageService.deleteFile(imgName);
    }

    @Override
    public Set<Image> findAll() {
        return imageRepository.findAll().stream().collect(Collectors.toSet());
    }
}
