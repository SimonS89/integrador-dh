package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.dto.request.BookingRegisterRequestDTO;
import com.example.workoutWonderland.dto.response.BookingDateRangeResponseDTO;
import com.example.workoutWonderland.entity.Booking;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.IBookingRepository;
import com.example.workoutWonderland.service.IBookingService;
import com.example.workoutWonderland.service.IEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookingService implements IBookingService {

    private final IBookingRepository bookingRepository;
    private final UserService userService;
    private final ProductService productService;
    private static final String NOT_FOUND = "Booking not found";
    private final ObjectMapper objectMapper;
    private final IEmailService emailService;

    public BookingService(IBookingRepository bookingRepository, UserService userService, ProductService productService, ObjectMapper objectMapper, IEmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.productService = productService;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    @Override
    public Booking create(BookingRegisterRequestDTO booking) throws AlreadyExistsException, ResourceNotFoundException {

        if (booking.getEndDate().isBefore(booking.getStartDate()))
            throw new IllegalArgumentException("The endDate must be after the startDate");
        Booking bookingCreate = new Booking();
        bookingCreate.setUser(userService.findById(booking.getUserId()));
        bookingCreate.setProduct(productService.findById(booking.getProductId()));
        bookingCreate.setStartDate(booking.getStartDate());
        bookingCreate.setEndDate(booking.getEndDate());
        Booking book = bookingRepository.save(bookingCreate);
        emailService.sendMail(book.getUser().getUsername(),"Booking information",emailBookingBody(book));
        return book;
    }

    @Override
    public Booking create(Booking booking) throws AlreadyExistsException {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findById(Long id) throws ResourceNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return booking.get();
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Booking> bookingSearch = bookingRepository.findById(id);
        if (bookingSearch.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        bookingRepository.deleteById(id);
    }

    @Override
    public Set<Booking> findAll() throws ResourceNotFoundException {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) throw new ResourceNotFoundException("There are no bookings stored");
        return new HashSet<>(bookings);
    }

    @Override
    public Booking modify(Booking booking) throws ResourceNotFoundException {
        getBooking(booking.getId());
        return bookingRepository.save(booking);
    }

    private Booking getBooking(Long id) throws ResourceNotFoundException {
        Optional<Booking> bookingSearch = bookingRepository.findById(id);
        if (bookingSearch.isEmpty()) throw new ResourceNotFoundException("Booking not found");
        return bookingSearch.get();
    }

    @Override
    public List<BookingDateRangeResponseDTO> findBookingsByProduct(long productId) throws ResourceNotFoundException {
        List<Booking> filteredBookings = bookingRepository.findByProductId(productId);
        if (filteredBookings.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return filteredBookings.stream().map(booking -> objectMapper.convertValue(booking, BookingDateRangeResponseDTO.class)).toList();
    }

    @Override
    public List<Booking> findBookingsByUser(long userId) throws ResourceNotFoundException {
        List<Booking> filteredBookings = bookingRepository.findBookingsByUser(userId);
        if (filteredBookings.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return filteredBookings;
    }

    private String emailBookingBody(Booking booking) {
        return "Dear " + booking.getUser().getLastname() + " " + booking.getUser().getName() + ", \n\n" +
                "Thank you for choosing Workout Wonderland for your ski product reservation! We are excited to have you as our customer and look forward to providing you with an exceptional mountain experience.\n" +
                "\n" +
                "Below we provide you with the details of your reservation:\n" +
                "\n" +
                "*Reservation Information:*\n" +
                "- Reservation Date: " + LocalDate.now() + "\n" +
                "- Reservation Number: "+booking.getId()+"\n"  +
                "- Email: "+booking.getUser().getUsername()+"\n" +
                "\n" +
                "*Reserved Product:*\n" +
                "- Product Type: "+booking.getProduct().getDescription()+"\n" +
                "- Quantity: 1\n" +
                "- Collection Date: "+booking.getStartDate()+"\n" +
                "- Return Date: "+booking.getEndDate()+"\n" +
                "\n" +
                "*Additional Details:*\n" +
                "- Other Notes or Special Requests: - \n" +
                "\n" +
                "*Pickup Instructions:*\n" +
                "[Provides details on the pickup location and time of the reserved products.]\n" +
                "\n" +
                "Please review your reservation details carefully. If you notice any errors or have any questions, do not hesitate to contact us immediately via this email.\n" +
                "\n" +
                "*Cancellation policy:*\n" +
                "If you need to cancel or modify your reservation, we ask that you notify us at least 2 days in advance. This will help us better serve you and other customers who may need our products.\n" +
                "\n" +
                "We look forward to seeing you at our Ski Resort and helping you have an exceptional skiing experience. If you have any other questions or need additional assistance, please do not hesitate to contact us at any time.\n" +
                "\n" +
                "Thank you for trusting Workout Wonderland for your ski needs. We wish you a fantastic day on the slopes!\n" +
                "\n" +
                "Sincerely,\n" +
                "\n" +
                "Ezequiel Agundez\n" +
                "Workout Wonderland\n" +
                "grupo6dh1511@gmail.com\n" +
                "http://grupo6california.s3-website-us-west-1.amazonaws.com/";
    }
}
