package org.phonetesting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.phonetesting.common.OS;
import org.phonetesting.dtos.*;
import org.phonetesting.exceptions.*;
import org.phonetesting.service.PhoneBookingService;
import org.phonetesting.persistence.Booking;
import org.phonetesting.persistence.Phone;
import org.phonetesting.persistence.User;
import org.phonetesting.repositories.BookingRepository;
import org.phonetesting.repositories.PhoneRepository;
import org.phonetesting.repositories.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
public class PhoneBookingServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PhoneBookingService phoneBookingService;

    @BeforeEach
    public void prepare() {
        bookingRepository.deleteAll();
        phoneRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void getAllPhones_whenNoPhones_thenReturnEmpty() {
        when(phoneRepository.findAll()).thenReturn(List.of());

        List<PhoneDTO> result = phoneBookingService.getAllPhones();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllPhones_whenHasPhones_thenReturnCorrectly() {
        when(phoneRepository.findAll())
                .thenReturn(List.of(new Phone(1L, "factory", "model", OS.ANDROID, "1.0", true)));

        List<PhoneDTO> result = phoneBookingService.getAllPhones();

        assertEquals(result.size(), 1);
        assertTrue(result.get(0).available());
        assertEquals(result.get(0).id(), 1L);
        assertEquals(result.get(0).spec(), new PhoneSpecDTO("factory", "model"));
    }

    @Test
    void getAvailablePhones_whenNoPhones_thenReturnEmpty() {
        when(phoneRepository.findByAvailable(true)).thenReturn(List.of());

        List<PhoneDTO> result = phoneBookingService.getAvailablePhones();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAvailablePhones_whenHasPhones_thenReturnCorrectly() {
        when(phoneRepository.findByAvailable(true))
                .thenReturn(List.of(new Phone(1L, "factory", "model", OS.ANDROID, "1.0", true)));

        List<PhoneDTO> result = phoneBookingService.getAvailablePhones();

        assertEquals(result.size(), 1);
        assertTrue(result.get(0).available());
        assertEquals(result.get(0).id(), 1L);
        assertEquals(result.get(0).spec(), new PhoneSpecDTO("factory", "model"));
    }

    @Test
    void getPhoneById_whenNoPhone_throwsPhoneNotFoundException() {
        when(phoneRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PhoneNotFoundException.class,() -> phoneBookingService.getPhoneById(1L));
    }

    @Test
    void getPhoneById_whenAvailable_thenReturnAvailable() {
        when(phoneRepository.findById(anyLong())).thenReturn(Optional.of(new Phone(1L, "factory", "model", OS.ANDROID, "1.0", true)));

        PhoneDetailedDTO result = phoneBookingService.getPhoneById(1L);

        assertNull(result.activeBooking());
        assertEquals(result.phone(),
                new PhoneDTO(1L, new PhoneSpecDTO("factory", "model"), new OsDTO(OS.ANDROID, "1.0"), true));
    }

    @Test
    void getPhoneById_whenUnavailable_thenReturnBookingData() {
        var phone = new Phone(1L, "factory", "model", OS.ANDROID, "1.0", false);
        when(phoneRepository.findById(anyLong())).thenReturn(Optional.of(phone));
        when(bookingRepository.findByPhoneIdAndReturnedAtIsNull(anyLong())).thenReturn(Optional.of(new Booking(2L, phone, new User(3L, "name", "email"), Instant.ofEpochMilli(1), null)));


        PhoneDetailedDTO result = phoneBookingService.getPhoneById(1L);

        assertEquals(result.activeBooking(), new ActiveBookingDTO(2L, new UserDTO( "name", "email"), Instant.ofEpochMilli(1L)));
        assertEquals(result.phone(),
                new PhoneDTO(1L, new PhoneSpecDTO("factory", "model"), new OsDTO(OS.ANDROID, "1.0"), false));
    }


    @Test
    public void bookPhoneBySpec_Success() {
        Phone testPhone = new Phone(1L, "Samsung", "Galaxy S10", OS.ANDROID, "4.5", true);
        User testUser = new User("Alice Smith", "alice@phonetesting.org");


        when(phoneRepository.findByManufacturerAndModelAndAvailable(anyString(), anyString(), anyBoolean()))
                .thenReturn(List.of(testPhone));
        when(phoneRepository.findByIdAndAvailable(1L, true))
                .thenReturn(Optional.of(testPhone));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking(1L, testPhone, testUser, Instant.now(), null));

        BookingDTO result = phoneBookingService.bookPhoneBySpec(new PhoneSpecDTO("testManufacturer", "testModel"), "alice@phonetesting.org");

        assertNotNull(result);
        verify(phoneRepository).save(testPhone);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    public void bookPhone_whenNoPhone_throwPhoneNotFoundException() {
        when(phoneRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(PhoneNotFoundException.class, () -> phoneBookingService.bookPhone(1L, "alice@phonetesting.org"));
    }

    @Test
    public void bookPhone_whenNotUser_throwUserNotFoundException() {
        Phone testPhone = new Phone(1L, "Samsung", "Galaxy S10", OS.ANDROID, "4.5", true);


        when(phoneRepository.findById(1L))
                .thenReturn(Optional.of(testPhone));
        when(phoneRepository.findByIdAndAvailable(1L, true))
                .thenReturn(Optional.of(testPhone));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> phoneBookingService.bookPhone(1L, "alice@phonetesting.org"));
    }

    @Test
    public void bookPhone_whenNotBooked_bookPhone() {
        Phone testPhone = new Phone(1L, "Samsung", "Galaxy S10", OS.ANDROID, "4.5", true);
        User testUser = new User("Alice Smith", "alice@phonetesting.org");


        when(phoneRepository.findById(1L))
                .thenReturn(Optional.of(testPhone));
        when(phoneRepository.findByIdAndAvailable(1L, true))
                .thenReturn(Optional.of(testPhone));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking(1L, testPhone, testUser, Instant.now(), null));

        BookingDTO result = phoneBookingService.bookPhone(1L, "alice@phonetesting.org");

        assertNotNull(result);
        verify(phoneRepository).save(testPhone);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    public void bookPhone_whenBooked_throwPhoneUnavailableException() {
        Phone testPhone = new Phone(1L, "Samsung", "Galaxy S10", OS.ANDROID, "4.5", false);

        when(phoneRepository.findById(anyLong()))
                .thenReturn(Optional.of(testPhone));
        when(phoneRepository.findByIdAndAvailable(1L, true))
                .thenReturn(Optional.empty());

        assertThrows(PhoneUnavailableException.class, () -> phoneBookingService.bookPhone(1L, "alice@phonetesting.org"));
    }

    @Test
    public void returnPhone_whenNoBooking_throwBookingNotFoundException() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> phoneBookingService.returnPhone(1L));
    }

    @Test
    public void returnPhone_whenAlreadyReturned_throwBookingAlreadyFinishedException() {
        Phone testPhone = new Phone(1L, "Samsung", "Galaxy S10", OS.ANDROID, "4.5", false);
        User testUser = new User("Alice Smith", "alice@phonetesting.org");

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(new Booking(1L, testPhone, testUser, Instant.ofEpochMilli(1L), Instant.ofEpochMilli(2L))));

        assertThrows(BookingAlreadyFinishedException.class, () -> phoneBookingService.returnPhone(1L));
    }

    @Test
    public void returnPhone_whenNotReturned_returnPhone() {
        Phone testPhone = new Phone(1L, "Samsung", "Galaxy S10", OS.ANDROID, "4.5", false);
        User testUser = new User("Alice Smith", "alice@phonetesting.org");
        Booking booking = new Booking(1L, testPhone, testUser, Instant.ofEpochMilli(1L), null);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        phoneBookingService.returnPhone(1L);

        testPhone.setAvailable(true);
        verify(phoneRepository).save(eq(testPhone));
        verify(bookingRepository).save(any(Booking.class));
    }
}
