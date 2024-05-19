package com.example.workoutWonderland.service;

import com.example.workoutWonderland.exception.ResourceNotFoundException;

public interface IEmailService {

    String sendMail(String to, String subject, String body) throws ResourceNotFoundException;
}
