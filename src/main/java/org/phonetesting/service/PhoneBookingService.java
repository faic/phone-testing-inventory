package org.phonetesting.service;

import org.phonetesting.dtos.*;
import org.phonetesting.exceptions.*;
import org.phonetesting.mappers.BookingMapper;
import org.phonetesting.mappers.PhoneDetailedMapper;
import org.phonetesting.mappers.PhoneMapper;
import org.phonetesting.persistence.Booking;
import org.phonetesting.persistence.Phone;
import org.phonetesting.persistence.User;
import org.phonetesting.repositories.BookingRepository;
import org.phonetesting.repositories.PhoneRepository;
import org.phonetesting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneBookingService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PhoneDTO> getAllPhones() {
        return phoneRepository.findAll().stream().map(PhoneMapper.INSTANCE::toPhoneDTO).collect(Collectors.toList());
    }

    public List<PhoneDTO> getAvailablePhones() {
        return phoneRepository.findByAvailable(true)
                .stream()
                .map(PhoneMapper.INSTANCE::toPhoneDTO)
                .collect(Collectors.toList());
    }

    public PhoneDetailedDTO getPhoneById(long phoneId) {
        return phoneRepository.findById(phoneId)
                .map(this::convertToPhoneDTOWithBooking)
                .orElseThrow(() -> new PhoneNotFoundException(phoneId));
    }

    private PhoneDetailedDTO convertToPhoneDTOWithBooking(Phone phone) {
        Booking booking = null;
        if (!phone.isAvailable()) {
            booking = bookingRepository.findByPhoneIdAndReturnedAtIsNull(phone.getId()).get();

        }
        return PhoneDetailedMapper.INSTANCE.toPhoneDetailedDTO(phone, booking);
    }


    @Transactional
    public BookingDTO bookPhoneBySpec(PhoneSpecDTO spec, String userEmail) {
        Phone phone = phoneRepository.findByManufacturerAndModelAndAvailable(spec.manufacturer(), spec.model(), true)
                .stream()
                .findFirst()
                .orElseThrow(PhoneUnavailableException::new);
        return bookPhoneInternal(phone, userEmail);
    }

    @Transactional
    public BookingDTO bookPhone(long phoneId, String userEmail) {
        Phone phoneToBook = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new PhoneNotFoundException(phoneId));
        return bookPhoneInternal(phoneToBook, userEmail);
    }

    private BookingDTO bookPhoneInternal(Phone phone, String userEmail) {
        phone = phoneRepository.findByIdAndAvailable(phone.getId(), true).orElseThrow(PhoneUnavailableException::new);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
        Booking booking = new Booking(phone, user);
        phone.setAvailable(false);
        phoneRepository.save(phone);
        return BookingMapper.INSTANCE.toBookingDTO(bookingRepository.save(booking));
    }

    @Transactional
    public void returnPhone(long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
        if (booking.getReturnedAt() != null) {
            throw new BookingAlreadyFinishedException(bookingId);
        }
        Phone phone = booking.getPhone();
        phone.setAvailable(true);
        booking.setReturnedAt(Instant.now());
        bookingRepository.save(booking);
        phoneRepository.save(phone);
    }
}
