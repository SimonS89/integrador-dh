package com.example.workoutWonderland.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.workoutWonderland.entity.Image;
import org.apache.commons.io.FileDeleteStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3client;

    public StorageService(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public Image uploadFile(MultipartFile image) throws IOException {
        File img = convertMultipartFileToFile(image);
        String imgName = System.currentTimeMillis() + "_" + image.getOriginalFilename().replaceAll("\\s", "");
        s3client.putObject(new PutObjectRequest(bucketName, imgName, img));
        String imgUrl = "https://grupo6california.s3.us-west-1.amazonaws.com/" + imgName;
        Image imageData = new Image();
        imageData.setUrl(imgUrl);
        imageData.setName(imgName);
        FileDeleteStrategy.FORCE.delete(img);
        return imageData;
    }

    public void deleteFile(String fileName) {
        s3client.deleteObject(bucketName, fileName);
    }

    private File convertMultipartFileToFile(MultipartFile image) throws IOException {
        File convertedImg = new File(image.getOriginalFilename());
        FileOutputStream imgOS = new FileOutputStream(convertedImg);
        imgOS.write(image.getBytes());
        imgOS.close();
        return convertedImg;
    }
}