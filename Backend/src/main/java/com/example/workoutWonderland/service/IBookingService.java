package com.example.workoutWonderland.service;

import com.example.workoutWonderland.dto.request.BookingRegisterRequestDTO;
import com.example.workoutWonderland.dto.response.BookingDateRangeResponseDTO;
import com.example.workoutWonderland.entity.Booking;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import io.jsonwebtoken.io.IOException;

import java.util.List;

public interface IBookingService extends IModelService<Booking>{
    Booking create(BookingRegisterRequestDTO booking) throws AlreadyExistsException, IOException,ResourceNotFoundException;
    List<BookingDateRangeResponseDTO> findBookingsByProduct(long productId) throws ResourceNotFoundException;
    List<Booking> findBookingsByUser(long userId) throws ResourceNotFoundException;
}
