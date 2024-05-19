package com.example.workoutWonderland.service;

import com.example.workoutWonderland.entity.Image;
import com.example.workoutWonderland.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService extends IModelService<Image> {

    Image createImage(MultipartFile img, Product product) throws IOException;

    void deleteFromAws(String imgName);
}
