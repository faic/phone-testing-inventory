package org.phonetesting.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.phonetesting.common.OS;
import org.phonetesting.persistance.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PhoneRepositoryTest {

    @Autowired
    private PhoneRepository phoneRepository;

    @BeforeEach
    public void prepare() {
        phoneRepository.deleteAll();
    }

    @Test
    public void findByAvailable_whenNoAvailable_returnsEmpty() {
        Phone phone1 = new Phone("Samsung", "Galaxy S24", OS.ANDROID, "14" ,false);
        Phone phone2 = new Phone("Apple", "iPhone 14", OS.IOS, "16" , false);
        phoneRepository.save(phone1);
        phoneRepository.save(phone2);

        List<Phone> availablePhones = phoneRepository.findByAvailable(true);

        assertEquals(0, availablePhones.size());
    }

    @Test
    public void findByAvailable_whenHasAvailable_returnsAvailable() {
        Phone phone1 = new Phone("Samsung", "Galaxy S24", OS.ANDROID, "14" ,true);
        Phone phone2 = new Phone("Apple", "iPhone 14", OS.IOS, "16" , false);
        phoneRepository.save(phone1);
        phoneRepository.save(phone2);

        List<Phone> availablePhones = phoneRepository.findByAvailable(true);

        assertEquals(1, availablePhones.size());
        assertEquals(phone1, availablePhones.get(0));
    }

    @Test
    public void findByManufacturerAndModelAndAvailable_whenNone_returnsEmpty() {
        Phone phone1 = new Phone("Samsung", "Galaxy S24", OS.ANDROID, "14" ,true);
        Phone phone2 = new Phone("Apple", "iPhone 14", OS.IOS, "16" , false);
        phoneRepository.save(phone1);
        phoneRepository.save(phone2);

        List<Phone> availablePhones = phoneRepository.findByManufacturerAndModelAndAvailable("Nokia", "3310", true);

        assertEquals(0, availablePhones.size());
    }

    @Test
    public void findByManufacturerAndModelAndAvailable_whenHasMatched_returnsMatched() {
        Phone phone1 = new Phone("Samsung", "Galaxy S24", OS.ANDROID, "14" ,true);
        Phone phone2 = new Phone("Apple", "iPhone 14", OS.IOS, "16" , true);
        phoneRepository.save(phone1);
        phoneRepository.save(phone2);

        List<Phone> availablePhones = phoneRepository.findByManufacturerAndModelAndAvailable("Samsung", "Galaxy S24",true);

        assertEquals(1, availablePhones.size());
        assertEquals(phone1, availablePhones.get(0));
    }

    @Test
    public void findByManufacturerAndModel_whenNoneMatch_returnsEmpty() {
        List<Phone> phones = phoneRepository.findByManufacturerAndModel("Samsung", "Galaxy S24");
        assertEquals(0, phones.size());
    }

    @Test
    public void findByManufacturerAndModel_whenHasMatched_returnsMatched() {
        Phone phone1 = new Phone("Samsung", "Galaxy S24", OS.ANDROID, "14" ,false);
        Phone phone2= new Phone("Apple", "iPhone 14", OS.IOS, "16" , false);
        phoneRepository.save(phone1);
        phoneRepository.save(phone2);


        List<Phone> phones = phoneRepository.findByManufacturerAndModel("Apple", "iPhone 14");

        assertEquals(1, phones.size());
        assertEquals(phone2, phones.get(0));
    }
}
