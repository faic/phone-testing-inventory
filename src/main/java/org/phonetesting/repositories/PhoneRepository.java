package org.phonetesting.repositories;

import jakarta.persistence.LockModeType;
import org.phonetesting.persistence.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByAvailable(boolean available);
    List<Phone> findByManufacturerAndModelAndAvailable(String manufacturer, String model, boolean available);
    List<Phone> findByManufacturerAndModel(String manufacturer, String model);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Phone> findByIdAndAvailable(long phoneId, boolean available);
}