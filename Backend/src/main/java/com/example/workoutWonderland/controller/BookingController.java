package com.example.workoutWonderland.controller;

import com.example.workoutWonderland.dto.response.BookingDateRangeResponseDTO;
import com.example.workoutWonderland.dto.request.BookingRegisterRequestDTO;
import com.example.workoutWonderland.entity.Booking;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.service.IBookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/booking")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRegisterRequestDTO booking) throws AlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(booking));
    }

    @GetMapping()
    public ResponseEntity<Set<Booking>> listBookings() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> bookingById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(bookingService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) throws ResourceNotFoundException {
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Booking deleted successfully");
    }

    @PutMapping("/modify")
    public ResponseEntity<Booking> updateBooking(@Valid @RequestBody Booking booking) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.modify(booking));
    }

//    @GetMapping("/by_product/{productId}")
//    public ResponseEntity<List<Booking>> listBookingsByProduct(@PathVariable Long productId) throws ResourceNotFoundException {
//        return ResponseEntity.status(HttpStatus.OK).body(bookingService.listBookingsByProduct(productId));
//    }

    @GetMapping("/by_product/{productId}")
    public ResponseEntity<List<BookingDateRangeResponseDTO>> listBookingsByProduct(@PathVariable Long productId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.findBookingsByProduct(productId));
    }

    @GetMapping("/by_user/{userId}")
    public ResponseEntity<List<Booking>> findBookingsByUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.findBookingsByUser(userId));
    }


}