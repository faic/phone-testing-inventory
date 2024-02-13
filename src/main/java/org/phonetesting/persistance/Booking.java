package org.phonetesting.persistance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phone_id", nullable = false)
    private Phone phone;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Timestamp bookedAt;

    private Timestamp returnedAt;

    // (for JPA)
    public Booking() {
    }

    public Booking(Long id, Phone phone, User user, Instant bookedAt, Instant returnedAt) {
        this.id = id;
        this.phone = phone;
        this.user = user;
        this.bookedAt = Timestamp.from(bookedAt);
        this.returnedAt = Optional.ofNullable(returnedAt).map(Timestamp::from).orElse(null);
    }

    public Booking(Phone phone, User user) {
        this.phone = phone;
        this.user = user;
        this.bookedAt = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getBookedAt() {
        return bookedAt.toInstant();
    }

    public void setBookedAt(Instant bookedAt) {
        this.bookedAt = Timestamp.from(bookedAt);
    }

    public Instant getReturnedAt() {
        return Optional.ofNullable(returnedAt).map(Timestamp::toInstant).orElse(null);
    }

    public void setReturnedAt(Instant returnedAt) {
        this.returnedAt = Timestamp.from(returnedAt);
    }
}

