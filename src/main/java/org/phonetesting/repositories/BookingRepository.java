package org.phonetesting.repositories;

import org.phonetesting.persistance.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByPhoneIdAndReturnedAtIsNull(long phoneId);
}
