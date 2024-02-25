package org.phonetesting.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.phonetesting.common.OS;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OS os;

    @Column(name = "os_version", nullable = false)
    private String osVersion;

    @Column(nullable = false)
    private boolean available;

    // (for JPA)
    public Phone() {
    }

    public Phone(String manufacturer, String model, OS os, String osVersion, boolean available) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.os = os;
        this.osVersion = osVersion;
        this.available = available;
    }

    public Phone(Long id, String manufacturer, String model, OS os, String osVersion, boolean available) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.os = os;
        this.osVersion = osVersion;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
