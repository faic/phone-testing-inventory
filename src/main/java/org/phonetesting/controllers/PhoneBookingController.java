package org.phonetesting.controllers;

import org.phonetesting.dtos.*;
import org.phonetesting.exceptions.*;
import org.phonetesting.service.PhoneBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneBookingController {

    @Autowired
    private PhoneBookingService phoneBookingService;

    @GetMapping
    public ResponseEntity<List<PhoneDTO>> getAllPhones() {
        List<PhoneDTO> phones = phoneBookingService.getAllPhones();
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/available")
    public ResponseEntity<List<PhoneDTO>> getAvailablePhones() {
        List<PhoneDTO> phones = phoneBookingService.getAvailablePhones();
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/{phoneId}")
    public ResponseEntity<Object> getPhoneById(@PathVariable("phoneId") Long phoneId) {
        try {
            return ResponseEntity.ok(phoneBookingService.getPhoneById(phoneId));
        } catch (PhoneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/book")
    public ResponseEntity<Object> bookPhone(@RequestBody BookingRequestDTO bookingRequest) {
        try {
            BookingDTO result = phoneBookingService.bookPhone(bookingRequest.phoneId(), bookingRequest.userEmail());
            return ResponseEntity.ok(result);
        } catch (PhoneNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PhoneUnavailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/book/spec")
    public ResponseEntity<Object> bookPhoneBySpec(@RequestBody BookingBySpecRequestDTO bookingRequest) {
        try {
            BookingDTO result = phoneBookingService.bookPhoneBySpec(
                    new PhoneSpecDTO(bookingRequest.manufacturer(), bookingRequest.model()),
                    bookingRequest.userEmail()
            );
            return ResponseEntity.ok(result);
        } catch (PhoneNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PhoneUnavailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/return/{bookingId}")
    public ResponseEntity<Object> returnPhone(@PathVariable("bookingId") Long bookingId) {
        try {
            phoneBookingService.returnPhone(bookingId);
            return ResponseEntity.ok("ok");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BookingAlreadyFinishedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
